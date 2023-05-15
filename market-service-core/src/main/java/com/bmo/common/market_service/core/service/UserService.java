package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.model.RegisterUserDto;

public interface UserService {
    User registerUser(RegisterUserDto registerUserDto);
}
