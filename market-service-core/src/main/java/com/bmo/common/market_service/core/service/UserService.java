package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.user.RegisterUserDto;
import com.bmo.common.market_service.model.user.UpdateUserDto;
import com.bmo.common.market_service.model.user.UsersFilterCriteria;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

public interface UserService {

  Page<User> getUsersFiltered(UsersFilterCriteria usersFilterCriteria, PageRequestDto pageRequest);

  User getUserById(UUID currentUserId);

  User registerUser(@NotNull UUID securityUserId, RegisterUserDto registerUserDto);

  User updateUserInfoById(UUID userId, UpdateUserDto updateUserDto);

  void deleteUserById(UUID userId);

  void recoverUserById(UUID userId);
}
