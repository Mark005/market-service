package com.bmo.common.market_service.core.mapper.order_details;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.OrderDetails;
import com.bmo.common.market_service.model.oreder_details.OrderDetailsResponseDto;
import org.mapstruct.Mapper;

@Mapper(config = MapStructCommonConfig.class)
public interface OrderDetailsResponseDtoMapper {

    OrderDetailsResponseDto map(OrderDetails orderDetails);
}
