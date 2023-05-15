package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.model.RegisterUserDto;
import com.bmo.common.market_service.model.RegisterUserResponseDto;
import org.mapstruct.Mapper;

@Mapper(config = MapStructCommonConfig.class)
public interface UserMapper {

    User map(RegisterUserDto registerUserDto);

    RegisterUserResponseDto map(User user);
}
