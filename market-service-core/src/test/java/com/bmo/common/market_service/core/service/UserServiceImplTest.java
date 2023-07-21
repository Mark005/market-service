package com.bmo.common.market_service.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bmo.common.auth_service.client.AuthServiceClient;
import com.bmo.common.auth_service.model.UpdateUserIdBody;
import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.core.dbmodel.enums.UserStatus;
import com.bmo.common.market_service.core.mapper.EnumMapper;
import com.bmo.common.market_service.core.mapper.EnumMapperImpl;
import com.bmo.common.market_service.core.mapper.PageableMapper;
import com.bmo.common.market_service.core.mapper.UserMapper;
import com.bmo.common.market_service.core.repository.CartRepository;
import com.bmo.common.market_service.core.repository.UserRepository;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.exception.EntityNotFoundException;
import com.bmo.common.market_service.model.user.RegisterUserDto;
import com.bmo.common.market_service.model.user.UpdateUserDto;
import com.bmo.common.market_service.model.user.UsersFilterCriteria;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private CartRepository cartRepository;

  @Mock
  private AuthServiceClient authServiceClient;

  @Mock
  private UserMapper userMapper;

  @Spy
  private EnumMapper enumMapper = new EnumMapperImpl();

  @InjectMocks
  private UserServiceImpl userService;

  @Test
  public void testGetUsersFiltered_ValidFilters_ReturnsFilteredUsers() {
    UsersFilterCriteria filter = new UsersFilterCriteria();
    PageRequestDto pageRequest = new PageRequestDto(1, 10);

    List<User> users = List.of(new User(), new User());
    Page<User> expectedPage = new PageImpl<>(users, PageableMapper.map(pageRequest), users.size());

    when(userRepository.findAll(any(Specification.class), eq(PageableMapper.map(pageRequest))))
        .thenReturn(expectedPage);

    Page<User> result = userService.getUsersFiltered(filter, pageRequest);

    assertNotNull(result);
    assertEquals(expectedPage, result);

    verify(userRepository)
        .findAll(any(Specification.class), eq(PageableMapper.map(pageRequest)));
  }

  @Test
  public void testGetUserById_UserExists_ReturnsUser() {
    UUID userId = UUID.randomUUID();
    User existingUser = new User();

    when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

    User result = userService.getUserById(userId);

    assertEquals(existingUser, result);

    verify(userRepository).findById(userId);
  }

  @Test
  public void testGetUserById_UserDoesNotExist_ThrowsEntityNotFoundException() {
    UUID userId = UUID.randomUUID();

    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class,
        () -> userService.getUserById(userId));

    verify(userRepository).findById(userId);
  }

  @Test
  public void testRegisterUser_ValidInput_ReturnsSavedUser() {
    UUID securityUserId = UUID.randomUUID();
    RegisterUserDto registerUserDto = new RegisterUserDto();

    User user = new User();

    when(userMapper.mapToEntity(securityUserId, registerUserDto)).thenReturn(user);
    when(userRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());
    when(cartRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

    User result = userService.registerUser(securityUserId, registerUserDto);

    assertEquals(user, result);

    verify(userRepository).save(
        argThat(userToSave -> userToSave.getStatus() == UserStatus.ACTIVE));
    verify(cartRepository).save(any());
    verify(authServiceClient).updateUserId(securityUserId, new UpdateUserIdBody(user.getId()));
  }

  @Test
  public void testUpdateUserInfoById_ValidInput_ReturnsUpdatedUser() {
    UUID userId = UUID.randomUUID();
    UpdateUserDto updateUserDto = new UpdateUserDto();
    User existingUser = new User();

    when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
    when(userMapper.merge(existingUser, updateUserDto)).thenReturn(existingUser);
    when(userRepository.save(existingUser)).thenReturn(existingUser);

    User result = userService.updateUserInfoById(userId, updateUserDto);

    assertNotNull(result);
    assertEquals(existingUser, result);

    verify(userRepository).findById(userId);
    verify(userMapper).merge(existingUser, updateUserDto);
    verify(userRepository).save(existingUser);
  }

  @Test
  public void testDeleteUserById_ValidInput_DeletesUser() {
    UUID userId = UUID.randomUUID();

    userService.deleteUserById(userId);

    verify(userRepository).updateStatusById(userId, UserStatus.DELETED);
  }

  @Test
  public void testRecoverUserById_ValidInput_RecoversUser() {
    UUID userId = UUID.randomUUID();

    userService.recoverUserById(userId);

    verify(userRepository).updateStatusById(userId, UserStatus.ACTIVE);
  }

}