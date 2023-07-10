package com.bmo.common.market_service.core.service;


import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.core.dbmodel.enums.UserStatus;
import com.bmo.common.market_service.model.exception.AccessDeniedException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonUserValidatorImpl implements CommonUserValidator {

  private final UserService userService;

  @Override
  public void validateUserNotDeleted(UUID userId) {
    User user = userService.getUserById(userId);
    if (user.getStatus() == UserStatus.DELETED) {
      throw new AccessDeniedException("User has been deleted");
    }
  }
}
