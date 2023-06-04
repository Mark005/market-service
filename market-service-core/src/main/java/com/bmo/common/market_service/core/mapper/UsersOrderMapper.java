package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.UsersOrder;
import com.bmo.common.market_service.model.oreder_details.OrderResponseDto;
import org.mapstruct.Mapper;

@Mapper(config = MapStructCommonConfig.class,
    uses = {CategoryMapper.class})
public interface UsersOrderMapper {


  OrderResponseDto mapToResponseDto(UsersOrder usersOrder);
}
