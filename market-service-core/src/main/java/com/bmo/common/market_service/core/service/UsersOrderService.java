package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.UsersOrder;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.oreder_details.OrderCreateDto;
import com.bmo.common.market_service.model.user.UsersFilterCriteria;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface UsersOrderService {

  Page<UsersOrder> getOrdersFiltered(UUID userId,
      UsersFilterCriteria usersFilterCriteria,
      PageRequestDto pageRequest);

  UsersOrder getOrderByIdAndUserId(UUID orderId, UUID userId);

  UsersOrder createOrder(UUID userId, OrderCreateDto orderCreateDto);

  UsersOrder cancelOrder(UUID userId, UUID orderId);
}
