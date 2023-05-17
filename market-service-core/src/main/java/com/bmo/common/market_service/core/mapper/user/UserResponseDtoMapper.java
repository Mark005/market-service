package com.bmo.common.market_service.core.mapper.user;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.model.user.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(config = MapStructCommonConfig.class)
public interface UserResponseDtoMapper {

    UserResponseDto map(User user);
}
