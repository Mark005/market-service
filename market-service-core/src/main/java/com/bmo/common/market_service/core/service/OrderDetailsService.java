package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.OrderDetails;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.user.UsersFilterCriteria;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface OrderDetailsService {

    Page<OrderDetails> getOrdersFiltered(UUID userId,
                                         UsersFilterCriteria usersFilterCriteria,
                                         PageRequestDto pageRequest);
}
