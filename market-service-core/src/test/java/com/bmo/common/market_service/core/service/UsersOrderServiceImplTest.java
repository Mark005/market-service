package com.bmo.common.market_service.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bmo.common.delivery_service.client.DeliveryServiceClient;
import com.bmo.common.delivery_service.model.rest.DeliveryResponseDto;
import com.bmo.common.delivery_service.model.rest.DeliveryStatusDto;
import com.bmo.common.delivery_service.model.rest.DeliveryStatusUpdateDto;
import com.bmo.common.market_service.core.dbmodel.Address;
import com.bmo.common.market_service.core.dbmodel.OrderHistory;
import com.bmo.common.market_service.core.dbmodel.Phone;
import com.bmo.common.market_service.core.dbmodel.Product;
import com.bmo.common.market_service.core.dbmodel.ProductItem;
import com.bmo.common.market_service.core.dbmodel.UsersOrder;
import com.bmo.common.market_service.core.dbmodel.enums.OrderStatus;
import com.bmo.common.market_service.core.dbmodel.enums.PaymentStatus;
import com.bmo.common.market_service.core.dbmodel.enums.PhoneType;
import com.bmo.common.market_service.core.dbmodel.enums.ProductItemStatus;
import com.bmo.common.market_service.core.mapper.OrderHistoryMapper;
import com.bmo.common.market_service.core.mapper.OrderHistoryMapperImpl;
import com.bmo.common.market_service.core.mapper.OrderInfoMapper;
import com.bmo.common.market_service.core.mapper.OrderInfoMapperImpl;
import com.bmo.common.market_service.core.mapper.delivery_service.DeliveryAddressMapper;
import com.bmo.common.market_service.core.mapper.delivery_service.DeliveryAddressMapperImpl;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class UsersOrderServiceImplTest {

  @Mock
  private UsersOrderRepository usersOrderRepository;

  @Mock
  private ProductItemRepository productItemRepository;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private PaymentDetailsRepository paymentDetailsRepository;

  @Mock
  private AddressRepository addressRepository;

  @Mock
  private PhoneRepository phoneRepository;

  @Mock
  private OrderHistoryRepository orderHistoryRepository;

  @Mock
  private DeliveryServiceClient deliveryServiceClient;

  @Spy
  private OrderInfoMapper orderInfoMapper = new OrderInfoMapperImpl();

  @Spy
  private DeliveryAddressMapper deliveryAddressMapper = new DeliveryAddressMapperImpl();

  @Spy
  private OrderHistoryMapper orderHistoryMapper = new OrderHistoryMapperImpl();

  @InjectMocks
  private UsersOrderServiceImpl usersOrderService;


  @Test
  public void testCreateOrder_ValidInput_ReturnsSavedUsersOrder() {
    UUID userId = UUID.randomUUID();
    UUID phoneId = UUID.randomUUID();
    UUID deliveryTypeId = UUID.randomUUID();
    UUID deliveryAddressId = UUID.randomUUID();
    UUID createdDeliveryId = UUID.randomUUID();

    UUID product1Id = UUID.randomUUID();
    UUID product2Id = UUID.randomUUID();

    UUID productItem1Id = UUID.randomUUID();
    UUID productItem2Id = UUID.randomUUID();
    UUID productItem3Id = UUID.randomUUID();

    UUID productItem4Id = UUID.randomUUID();
    UUID productItem5Id = UUID.randomUUID();

    OrderProduct orderProduct1 = new OrderProduct(product1Id, 3);
    OrderProduct orderProduct2 = new OrderProduct(product2Id, 2);
    List<OrderProduct> orderProducts = List.of(orderProduct1, orderProduct2);
    OrderCreateDto orderCreateDto = OrderCreateDto.builder()
        .orderProducts(orderProducts)
        .deliveryTypeId(deliveryTypeId)
        .deliveryAddressId(deliveryAddressId)
        .contactPhoneId(phoneId)
        .build();

    Product product1 = Product.builder()
        .id(product1Id)
        .name("Product 1")
        .price(new BigDecimal(111))
        .build();
    Product product2 = Product.builder()
        .id(product2Id)
        .name("Product 2")
        .price(new BigDecimal(171))
        .build();

    List<Product> products = List.of(product1, product2);

    ProductItem productItem1 = ProductItem.builder()
        .id(productItem1Id)
        .status(ProductItemStatus.AVAILABLE)
        .build();
    ProductItem productItem2 = ProductItem.builder()
        .id(productItem2Id)
        .status(ProductItemStatus.AVAILABLE)
        .build();
    ProductItem productItem3 = ProductItem.builder()
        .id(productItem3Id)
        .status(ProductItemStatus.AVAILABLE)
        .build();

    ProductItem productItem4 = ProductItem.builder()
        .id(productItem4Id)
        .status(ProductItemStatus.AVAILABLE)
        .build();
    ProductItem productItem5 = ProductItem.builder()
        .id(productItem5Id)
        .status(ProductItemStatus.AVAILABLE)
        .build();

    List<ProductItem> productItems1 = List.of(productItem1, productItem2, productItem3);
    List<ProductItem> productItems2 = List.of(productItem4, productItem5);

    Address deliveryAddress = Address.builder()
        .country("country")
        .city("city")
        .street("street")
        .building("building")
        .build();

    Phone contactPhone = Phone.builder()
        .phoneNumber("+1234567890")
        .type(PhoneType.MOBILE)
        .build();

    DeliveryResponseDto deliveryResponseDto = DeliveryResponseDto.builder()
        .id(createdDeliveryId)
        .build();

    when(productRepository.findAllById(any())).thenReturn(products);
    when(productItemRepository.findAllByProductIdAndStatus(product1Id, ProductItemStatus.AVAILABLE,
        Pageable.ofSize(3)))
        .thenReturn(productItems1);
    when(productItemRepository.findAllByProductIdAndStatus(product2Id, ProductItemStatus.AVAILABLE,
        Pageable.ofSize(2)))
        .thenReturn(productItems2);
    when(productItemRepository.countByProductIdAndStatus(any(), any())).thenReturn(10);
    when(addressRepository.findByIdAndUserId(deliveryAddressId, userId)).thenReturn(
        Optional.of(deliveryAddress));
    when(phoneRepository.findByIdAndUserId(phoneId, userId)).thenReturn(Optional.of(contactPhone));
    when(paymentDetailsRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
    when(usersOrderRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
    when(deliveryServiceClient.createDelivery(any(), any(), any())).thenReturn(deliveryResponseDto);

    UsersOrder createdOrder = usersOrderService.createOrder(userId, orderCreateDto);

    assertEquals(createdDeliveryId, createdOrder.getDeliveryId());
    assertEquals(OrderStatus.ORDERED, createdOrder.getStatus());
    assertNotNull(createdOrder.getOrderDateTime());
    assertNotNull(createdOrder.getLastUpdateDateTime());
    assertNotNull(createdOrder.getLastUpdateDateTime());
    assertEquals(new BigDecimal(675), createdOrder.getOrderInfo().getProductsPrice());
    assertEquals(new BigDecimal(675), createdOrder.getPaymentDetails().getAmount());
    assertEquals(PaymentStatus.REQUIRED, createdOrder.getPaymentDetails().getPaymentStatus());
    assertEquals(2, createdOrder.getOrderInfo().getProducts().size());
    assertEquals(5, createdOrder.getProductItems().size());
    assertEquals(userId, createdOrder.getUser().getId());
    assertEquals(0, createdOrder.getOrderHistories().size());

    verify(usersOrderRepository, times(2)).save(any());
    verify(productItemRepository).saveAll(any());
    verify(paymentDetailsRepository).save(any());
    verify(deliveryServiceClient).createDelivery(any(), any(), any());
    verify(orderHistoryRepository).save(any());
  }


  @Test
  public void testGetOrdersFiltered() {
    UUID userId = UUID.randomUUID();
    Pageable pageRequest = Pageable.ofSize(3);

    when(usersOrderRepository.findAllByUserId(userId, pageRequest)).thenReturn(Page.empty());

    Page<UsersOrder> resultPage = usersOrderService.getOrdersFiltered(
        userId,
        new UsersFilterCriteria(),
        new PageRequestDto(3, 0));

    verify(usersOrderRepository).findAllByUserId(userId, pageRequest);

    assertEquals(Page.empty(), resultPage);
  }

  @Test
  public void testGetOrderById_ExistingOrder_Success() {
    UUID orderId = UUID.randomUUID();
    UsersOrder mockOrder = new UsersOrder();

    when(usersOrderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

    UsersOrder result = usersOrderService.getOrderById(orderId);

    verify(usersOrderRepository).findById(orderId);

    assertEquals(mockOrder, result);
  }

  @Test
  public void testGetOrderById_OrderNotFound_ThrowsException() {
    UUID orderId = UUID.randomUUID();

    when(usersOrderRepository.findById(orderId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> usersOrderService.getOrderById(orderId));

    verify(usersOrderRepository).findById(orderId);
  }

  @Test
  public void testGetOrderByIdAndUserId_ExistingOrder_Success() {
    UUID orderId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    UsersOrder mockOrder = new UsersOrder();
    when(usersOrderRepository.findByIdAndUserId(orderId, userId)).thenReturn(
        Optional.of(mockOrder));

    UsersOrder result = usersOrderService.getOrderByIdAndUserId(orderId, userId);

    verify(usersOrderRepository).findByIdAndUserId(orderId, userId);

    assertEquals(mockOrder, result);
  }

  @Test
  public void testGetOrderByIdAndUserId_OrderNotFound_ThrowsException() {
    UUID orderId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    when(usersOrderRepository.findByIdAndUserId(orderId, userId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class,
        () -> usersOrderService.getOrderByIdAndUserId(orderId, userId));

    verify(usersOrderRepository).findByIdAndUserId(orderId, userId);
  }

  @Test
  public void testCancelOrder_OrderStatusOrdered_Success() {
    UUID userId = UUID.randomUUID();
    UUID orderId = UUID.randomUUID();
    UsersOrder order = new UsersOrder();
    order.setStatus(OrderStatus.ORDERED);

    when(usersOrderRepository.findByIdAndUserId(orderId, userId)).thenReturn(Optional.of(order));
    when(usersOrderRepository.save(any(UsersOrder.class))).thenReturn(order);

    when(productItemRepository.saveAll(any())).then(
        invocation -> new ArrayList<>((Set) invocation.getArguments()[0]));

    UsersOrder result = usersOrderService.cancelOrder(userId, orderId);

    verify(usersOrderRepository).findByIdAndUserId(orderId, userId);
    verify(usersOrderRepository).save(order);
    verify(productItemRepository).saveAll(any());
    verify(deliveryServiceClient)
        .updateDeliveryStatus(userId, orderId,
            new DeliveryStatusUpdateDto(DeliveryStatusDto.CANCELED));

    assertEquals(OrderStatus.CANCELLED, result.getStatus());
  }

  @Test
  public void testCancelOrder_OrderStatusNotOrdered_ThrowsException() {
    UUID userId = UUID.randomUUID();
    UUID orderId = UUID.randomUUID();
    UsersOrder usersOrder = new UsersOrder();
    usersOrder.setStatus(OrderStatus.PAYMENT_PENDING);

    when(usersOrderRepository.findByIdAndUserId(orderId, userId)).thenReturn(
        Optional.of(usersOrder));

    assertThrows(
        MarketServiceBusinessException.class, () -> usersOrderService.cancelOrder(userId, orderId));

    verify(usersOrderRepository).findByIdAndUserId(orderId, userId);
    verify(usersOrderRepository, never()).save(any());
    verify(productItemRepository, never()).saveAll(any());
    verify(deliveryServiceClient, never()).updateDeliveryStatus(any(), any(), any());
  }

  @Test
  public void testSaveAndUpdateHistory_Success() {
    UsersOrder usersOrder = new UsersOrder();
    OrderHistory orderHistory = new OrderHistory();
    when(usersOrderRepository.save(usersOrder)).thenReturn(usersOrder);
    when(orderHistoryRepository.save(any(OrderHistory.class))).thenReturn(orderHistory);

    UsersOrder result = usersOrderService.saveAndUpdateHistory(usersOrder);

    verify(usersOrderRepository).save(any());
    verify(orderHistoryRepository).save(any());

    assertEquals(usersOrder, result);
  }
}