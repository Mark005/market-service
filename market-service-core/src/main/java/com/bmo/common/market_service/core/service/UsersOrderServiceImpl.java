package com.bmo.common.market_service.core.service;

import com.bmo.common.delivery_service.client.DeliveryServiceClient;
import com.bmo.common.delivery_service.model.rest.ContactPhoneDto;
import com.bmo.common.delivery_service.model.rest.DeliveryAddressDto;
import com.bmo.common.delivery_service.model.rest.DeliveryCreateDto;
import com.bmo.common.delivery_service.model.rest.DeliveryResponseDto;
import com.bmo.common.delivery_service.model.rest.DeliveryStatusDto;
import com.bmo.common.delivery_service.model.rest.DeliveryStatusUpdateDto;
import com.bmo.common.market_service.core.dbmodel.OrderHistory;
import com.bmo.common.market_service.core.dbmodel.OrderInfo;
import com.bmo.common.market_service.core.dbmodel.PaymentDetails;
import com.bmo.common.market_service.core.dbmodel.Product;
import com.bmo.common.market_service.core.dbmodel.ProductItem;
import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.core.dbmodel.UsersOrder;
import com.bmo.common.market_service.core.dbmodel.enums.OrderStatus;
import com.bmo.common.market_service.core.dbmodel.enums.PaymentStatus;
import com.bmo.common.market_service.core.dbmodel.enums.ProductItemStatus;
import com.bmo.common.market_service.core.mapper.OrderHistoryMapper;
import com.bmo.common.market_service.core.mapper.OrderInfoMapper;
import com.bmo.common.market_service.core.mapper.PageableMapper;
import com.bmo.common.market_service.core.mapper.delivery_service.DeliveryAddressMapper;
import com.bmo.common.market_service.core.repository.AddressRepository;
import com.bmo.common.market_service.core.repository.OrderHistoryRepository;
import com.bmo.common.market_service.core.repository.PaymentDetailsRepository;
import com.bmo.common.market_service.core.repository.PhoneRepository;
import com.bmo.common.market_service.core.repository.ProductItemRepository;
import com.bmo.common.market_service.core.repository.ProductRepository;
import com.bmo.common.market_service.core.repository.UsersOrderRepository;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.exception.EntityNotFoundException;
import com.bmo.common.market_service.model.exception.MarketServiceBusinessException;
import com.bmo.common.market_service.model.user.UsersFilterCriteria;
import com.bmo.common.market_service.model.users_oreder.OrderCreateDto;
import com.bmo.common.market_service.model.users_oreder.OrderProduct;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersOrderServiceImpl implements UsersOrderService {

  private final UsersOrderRepository usersOrderRepository;
  private final ProductItemRepository productItemRepository;
  private final ProductRepository productRepository;
  private final PaymentDetailsRepository paymentDetailsRepository;
  private final AddressRepository addressRepository;
  private final PhoneRepository phoneRepository;
  private final OrderHistoryRepository orderHistoryRepository;

  private final DeliveryServiceClient deliveryServiceClient;

  private final OrderInfoMapper orderInfoMapper;
  private final DeliveryAddressMapper deliveryAddressMapper;
  private final OrderHistoryMapper orderHistoryMapper;

  @Override
  public UsersOrder createOrder(UUID userId, OrderCreateDto orderCreateDto) {
    Set<ProductItem> productItems = bindWithProductItems(orderCreateDto);

    List<OrderProduct> orderProducts = orderCreateDto.getOrderProducts();

    Map<UUID, Integer> productIdToQuantity = orderProducts.stream()
        .collect(Collectors.toMap(
            OrderProduct::getProductId,
            OrderProduct::getQuantity));

    Map<Product, Integer> productToQuantity = productRepository.findAllById(productIdToQuantity.keySet())
        .stream()
        .collect(Collectors.toMap(
            Function.identity(),
            product -> productIdToQuantity.get(product.getId())));

    OrderInfo orderInfo = orderInfoMapper.map(productToQuantity);
    PaymentDetails paymentDetails = generatePaymentDetails(orderInfo);

    ZonedDateTime dateTimeNow = ZonedDateTime.now();
    UsersOrder usersOrder = UsersOrder.builder()
        .orderInfo(orderInfo)
        .status(OrderStatus.ORDERED)
        .orderDateTime(dateTimeNow)
        .lastUpdateDateTime(dateTimeNow)
        .productItems(productItems)
        .paymentDetails(paymentDetails)
        .user(User.builder().id(userId).build())
        .build();

    UsersOrder savedUsersOrder = usersOrderRepository.save(usersOrder);

    productItems.forEach(productItem -> productItem.setUsersOrder(usersOrder));
    productItemRepository.saveAll(productItems);

    paymentDetails.setUsersOrder(savedUsersOrder);
    paymentDetailsRepository.save(paymentDetails);

    savedUsersOrder = createDeliveryAndUpdateOrder(userId,
        orderCreateDto.getDeliveryTypeId(),
        orderCreateDto.getDeliveryAddressId(),
        orderCreateDto.getContactPhoneId(),
        savedUsersOrder);

    return savedUsersOrder;
  }

  private UsersOrder createDeliveryAndUpdateOrder(
      UUID userId,
      UUID deliveryTypeId,
      @Nullable UUID deliveryAddressId,
      UUID contactPhoneId,
      UsersOrder usersOrder) {

    DeliveryAddressDto deliveryAddressDto =
        Optional.ofNullable(deliveryAddressId)
            .map(addressId -> addressRepository.findByIdAndUserId(deliveryAddressId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Address", deliveryAddressId)))
            .map(deliveryAddressMapper::mapAddress)
            .orElse(null);

    ContactPhoneDto contactPhoneDto = phoneRepository.findByIdAndUserId(contactPhoneId, userId)
        .map(deliveryAddressMapper::mapPhone)
        .orElseThrow(() -> new EntityNotFoundException("Phone", deliveryAddressId));

    DeliveryCreateDto deliveryCreateDto = DeliveryCreateDto.builder()
        .deliveryTypeId(deliveryTypeId)
        .deliveryAddress(deliveryAddressDto)
        .contactPhone(contactPhoneDto)
        .build();

    DeliveryResponseDto deliveryResponseDto = deliveryServiceClient.createDelivery(userId, usersOrder.getId(),
        deliveryCreateDto);

    usersOrder.setDeliveryId(deliveryResponseDto.getId());

    return saveAndUpdateHistory(usersOrder);
  }


  private PaymentDetails generatePaymentDetails(OrderInfo  orderInfo) {
    return PaymentDetails.builder()
        .paymentStatus(PaymentStatus.REQUIRED)
        .amount(orderInfo.getProductsPrice())
        .build();
  }

  private Set<ProductItem> bindWithProductItems(OrderCreateDto orderCreateDto) {
    Set<ProductItem> productItems = new HashSet<>();

    orderCreateDto.getOrderProducts().forEach(orderProduct -> {
      Integer countOfAvailable =
          productItemRepository.countByProductIdAndStatus(
              orderProduct.getProductId(),
              ProductItemStatus.AVAILABLE);

      if (countOfAvailable < orderProduct.getQuantity()) {
        throw new MarketServiceBusinessException(
            "Count of available [%s], count of requested [%s]".formatted(
                countOfAvailable,
                orderProduct.getQuantity()));
      }

      List<ProductItem> productItemsForOrder = productItemRepository.findAllByProductIdAndStatus(
          orderProduct.getProductId(),
          ProductItemStatus.AVAILABLE,
          Pageable.ofSize(orderProduct.getQuantity()));

      productItemsForOrder.forEach(productItem -> {
        productItem.setStatus(ProductItemStatus.BOOKED);
      });

      productItems.addAll(productItemsForOrder);
    });

    return productItems;
  }


  @Override
  public Page<UsersOrder> getOrdersFiltered(UUID userId,
      UsersFilterCriteria usersFilterCriteria,
      PageRequestDto pageRequest) {
    return usersOrderRepository.findAllByUserId(userId, PageableMapper.map(pageRequest));
  }

  @Override
  public UsersOrder getOrderById(UUID orderId) {
    return usersOrderRepository.findById(orderId)
        .orElseThrow(() -> new EntityNotFoundException("UsersOrder", orderId));
  }

  @Override
  public UsersOrder getOrderByIdAndUserId(UUID orderId, UUID userId) {
    return usersOrderRepository.findByIdAndUserId(orderId, userId)
        .orElseThrow(() -> new EntityNotFoundException(
            "UsersOrder with id [%s] for user with id [%s] not found"
                .formatted(orderId, userId)));
  }

  @Override
  public UsersOrder cancelOrder(UUID userId, UUID orderId) {
    UsersOrder usersOrder = getOrderByIdAndUserId(orderId, userId);

    if (usersOrder.getStatus() != OrderStatus.ORDERED) {
      throw new MarketServiceBusinessException("Order can not be cancelled");
    }

    usersOrder.setStatus(OrderStatus.CANCELLED);

    Set<ProductItem> productItems = usersOrder.getProductItems();
    productItems.forEach(productItem -> {
      productItem.setStatus(ProductItemStatus.AVAILABLE);
      productItem.setUsersOrder(null);
    });
    usersOrder.getProductItems().clear();

    productItemRepository.saveAll(productItems);
    UsersOrder savedUsersOrder = saveAndUpdateHistory(usersOrder);

    DeliveryStatusUpdateDto canceledStatusDto = DeliveryStatusUpdateDto.builder().status(
        DeliveryStatusDto.CANCELED).build();
    deliveryServiceClient.updateDeliveryStatus(userId, orderId, canceledStatusDto);
    return savedUsersOrder;
  }

  @Override
  public UsersOrder saveAndUpdateHistory(UsersOrder usersOrder) {
    UsersOrder saved = usersOrderRepository.save(usersOrder);

    OrderHistory newOrderHistory = orderHistoryMapper.mapHistoryRecord(usersOrder);
    orderHistoryRepository.save(newOrderHistory);
    return saved;
  }
}
