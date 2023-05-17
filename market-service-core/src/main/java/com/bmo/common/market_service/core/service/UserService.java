package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.model.user.RegisterUserDto;
import com.bmo.common.market_service.model.user.UpdateUserDto;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public interface UserService {
    User registerUser(@NotNull UUID securityUserId, RegisterUserDto registerUserDto);

    User getUserById(UUID currentUserId);

    User updateUserInfoById(UUID userId, UpdateUserDto updateUserDto);

    void deleteUserById(UUID userId);

    void recoverUserById(UUID userId);
}
