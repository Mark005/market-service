package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.UsersOrder;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.user.UsersFilterCriteria;
import com.bmo.common.market_service.model.users_oreder.OrderCreateDto;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface UsersOrderService {

  Page<UsersOrder> getOrdersFiltered(UUID userId,
      UsersFilterCriteria usersFilterCriteria,
      PageRequestDto pageRequest);

  UsersOrder getOrderById(UUID orderId);

  UsersOrder getOrderByIdAndUserId(UUID orderId, UUID userId);

  UsersOrder createOrder(UUID userId, OrderCreateDto orderCreateDto);

  UsersOrder cancelOrder(UUID userId, UUID orderId);

  UsersOrder saveAndUpdateHistory(UsersOrder usersOrder);
}
