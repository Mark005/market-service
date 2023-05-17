package com.bmo.common.market_service.core.mapper.user;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.model.user.RegisterUserDto;
import com.bmo.common.market_service.model.user.UpdateUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(config = MapStructCommonConfig.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "phones", ignore = true)
    @Mapping(target = "cart", ignore = true)
    User map(UUID securityUserId, RegisterUserDto registerUserDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "securityUserId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "phones", ignore = true)
    @Mapping(target = "cart", ignore = true)
    User merge(@MappingTarget User user, UpdateUserDto updateUserDto);
}
