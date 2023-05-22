package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.OrderDetails;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.oreder_details.OrderCreateDto;
import com.bmo.common.market_service.model.user.UsersFilterCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Override
    public Page<OrderDetails> getOrdersFiltered(UUID userId,
                                                UsersFilterCriteria usersFilterCriteria,
                                                PageRequestDto pageRequest) {
        //ToDo
        return null;
    }

    @Override
    public OrderDetails getOrderByIdAndUserId(UUID orderId, UUID currentUserId) {
        //ToDo
        return null;
    }

    @Override
    public OrderDetails createOrder(UUID currentUserId, OrderCreateDto orderCreateDto) {
        //ToDo
        return null;
    }

    @Override
    public OrderDetails cancelOrder(UUID userId, UUID orderId) {
        //ToDo
        return null;
    }
}
