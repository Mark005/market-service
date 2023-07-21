package com.bmo.common.market_service.core.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.core.dbmodel.enums.UserStatus;
import com.bmo.common.market_service.model.exception.AccessDeniedException;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommonUserValidatorImplTest {

  @Mock
  private UserService userService;

  @InjectMocks
  private CommonUserValidatorImpl commonUserValidator;

  @Test
  public void testValidateUserNotDeleted_UserNotDeleted_NoExceptionThrown() {
    UUID userId = UUID.randomUUID();
    User user = new User();

    when(userService.getUserById(userId)).thenReturn(user);

    assertDoesNotThrow(() -> commonUserValidator.validateUserNotDeleted(userId));

    verify(userService).getUserById(userId);
  }

  @Test
  public void testValidateUserNotDeleted_UserDeleted_ThrowsAccessDeniedException() {
    UUID userId = UUID.randomUUID();
    User user = new User();
    user.setStatus(UserStatus.DELETED);

    when(userService.getUserById(userId)).thenReturn(user);

    assertThrows(AccessDeniedException.class,
        () -> commonUserValidator.validateUserNotDeleted(userId));

    verify(userService).getUserById(userId);
  }

}