package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.UsersOrder;
import com.bmo.common.market_service.core.dbmodel.ProductItem;
import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.core.dbmodel.enums.OrderStatus;
import com.bmo.common.market_service.core.dbmodel.enums.ProductItemStatus;
import com.bmo.common.market_service.core.repository.UsersOrderRepository;
import com.bmo.common.market_service.core.repository.ProductItemRepository;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.exception.EntityNotFoundException;
import com.bmo.common.market_service.model.exception.MarketServiceBusinessException;
import com.bmo.common.market_service.model.oreder_details.OrderCreateDto;
import com.bmo.common.market_service.model.user.UsersFilterCriteria;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersOrderServiceImpl implements UsersOrderService {

  private final UsersOrderRepository usersOrderRepository;
  private final ProductItemRepository productItemRepository;

  @Override
  public UsersOrder createOrder(UUID userId, OrderCreateDto orderCreateDto) {
    Set<ProductItem> productItems = new HashSet<>();

    orderCreateDto.getOrderProducts().forEach(orderProduct -> {
      Integer countOfAvailable =
          productItemRepository.countByProductIdAndStatus(
              orderProduct.getProductId(),
              ProductItemStatus.AVAILABLE);

      if (countOfAvailable < orderProduct.getCount()) {
        throw new MarketServiceBusinessException(
            "Count of available [%s], count of requested [%s]".formatted(
                countOfAvailable,
                orderProduct.getCount()));
      }

      ProductItem productItemForOrder = productItemRepository.findFirstByProductIdAndStatus(
          orderProduct.getProductId(),
          ProductItemStatus.AVAILABLE);

      productItemForOrder.setStatus(ProductItemStatus.BOOKED);
      productItems.add(productItemForOrder);
    });
    ZonedDateTime dateTimeNow = ZonedDateTime.now();
    UsersOrder usersOrder = UsersOrder.builder()
        .orderStatus(OrderStatus.ORDERED)
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
    //ToDo
    return null;
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
    //ToDo
    return null;
  }
}
