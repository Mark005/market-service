package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Product;
import com.bmo.common.market_service.core.dbmodel.ProductItem;
import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.core.dbmodel.UsersOrder;
import com.bmo.common.market_service.core.dbmodel.enums.OrderStatus;
import com.bmo.common.market_service.core.dbmodel.enums.ProductItemStatus;
import com.bmo.common.market_service.core.mapper.PageableMapper;
import com.bmo.common.market_service.core.mapper.PresentableInfoMapper;
import com.bmo.common.market_service.core.repository.ProductItemRepository;
import com.bmo.common.market_service.core.repository.UsersOrderRepository;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.exception.EntityNotFoundException;
import com.bmo.common.market_service.model.exception.MarketServiceBusinessException;
import com.bmo.common.market_service.model.user.UsersFilterCriteria;
import com.bmo.common.market_service.model.users_oreder.OrderCreateDto;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersOrderServiceImpl implements UsersOrderService {

  private final UsersOrderRepository usersOrderRepository;
  private final ProductItemRepository productItemRepository;

  private final PresentableInfoMapper presentableInfoMapper;

  @Override
  public UsersOrder createOrder(UUID userId, OrderCreateDto orderCreateDto) {
    Set<ProductItem> productItems = new HashSet<>();
    Map<Product, Integer> productToQuantity = new HashMap<>();

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
        productToQuantity.put(productItem.getProduct(), orderProduct.getQuantity());
      });

      productItems.addAll(productItemsForOrder);
    });
    ZonedDateTime dateTimeNow = ZonedDateTime.now();
    UsersOrder usersOrder = UsersOrder.builder()
        .presentableInfo(presentableInfoMapper.map(productToQuantity))
        .status(OrderStatus.ORDERED)
        .orderDateTime(dateTimeNow)
        .lastUpdateDateTime(dateTimeNow)
        .productItems(productItems)
        .user(User.builder().id(userId).build())
        .build();

    UsersOrder savedUsersOrder = usersOrderRepository.save(usersOrder);

    productItems.forEach(productItem -> productItem.setUsersOrder(usersOrder));
    productItemRepository.saveAll(productItems);

    return savedUsersOrder;
  }


  @Override
  public Page<UsersOrder> getOrdersFiltered(UUID userId,
      UsersFilterCriteria usersFilterCriteria,
      PageRequestDto pageRequest) {
    return usersOrderRepository.findAllByUserId(userId, PageableMapper.map(pageRequest));
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
      throw new MarketServiceBusinessException("Order cant be cancelled");
    }

    usersOrder.setStatus(OrderStatus.CANCELLED);

    Set<ProductItem> productItems = usersOrder.getProductItems();
    productItems.forEach(productItem -> {
      productItem.setStatus(ProductItemStatus.AVAILABLE);
      productItem.setUsersOrder(null);
    });

    productItemRepository.saveAll(productItems);
    usersOrderRepository.save(usersOrder);
    return usersOrder;
  }
}
