package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.OrderDetails;
import com.bmo.common.market_service.model.oreder_details.OrderDetailsResponseDto;
import org.mapstruct.Mapper;

@Mapper(config = MapStructCommonConfig.class,
    uses = {CategoryMapper.class})
public interface OrderDetailsMapper {


  OrderDetailsResponseDto mapToResponseDto(OrderDetails orderDetails);
}
