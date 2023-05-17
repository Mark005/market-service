package com.bmo.common.market_service.core.mapper.user;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.model.user.RegisterUserResponseDto;
import org.mapstruct.Mapper;

@Mapper(config = MapStructCommonConfig.class)
public interface RegisterUserResponseDtoMapper {
    RegisterUserResponseDto map(User user);
}
