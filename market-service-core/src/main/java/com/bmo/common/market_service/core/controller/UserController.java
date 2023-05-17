package com.bmo.common.market_service.core.controller;

import com.bmo.common.gateway.header.GatewayHeader;
import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.core.mapper.user.RegisterUserResponseDtoMapper;
import com.bmo.common.market_service.core.mapper.user.UserResponseDtoMapper;
import com.bmo.common.market_service.core.service.UserService;
import com.bmo.common.market_service.model.user.RegisterUserDto;
import com.bmo.common.market_service.model.user.RegisterUserResponseDto;
import com.bmo.common.market_service.model.user.UpdateUserDto;
import com.bmo.common.market_service.model.user.UserResponseDto;
import com.bmo.common.market_service.model.user.UsersFilterCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserResponseDtoMapper userResponseDtoMapper;
    private final RegisterUserResponseDtoMapper registerUserResponseDtoMapper;

    @GetMapping("/users")
    public ResponseEntity<Page<UserResponseDto>> getUsersFiltered(UsersFilterCriteria usersFilterCriteria) {

        Page<User> users = userService.getUsersFiltered(usersFilterCriteria);
        Page<UserResponseDto> responseDto = users.map(userResponseDtoMapper::map);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/users/current")
    public ResponseEntity<UserResponseDto> getCurrentUser(
            @RequestHeader(GatewayHeader.USER_ID) UUID currentUserId) {

        User user = userService.getUserById(currentUserId);
        UserResponseDto responseDto = userResponseDtoMapper.map(user);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(
            @NotNull @PathVariable("userId") UUID userId) {

        User user = userService.getUserById(userId);
        UserResponseDto responseDto = userResponseDtoMapper.map(user);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/users/register")
    public ResponseEntity<RegisterUserResponseDto> registerUser(
            @NotNull @RequestHeader(GatewayHeader.SECURITY_USER_ID) UUID securityUserId,
            @RequestBody @Valid RegisterUserDto registerUserDto) {

        User user = userService.registerUser(securityUserId, registerUserDto);
        RegisterUserResponseDto responseDto = registerUserResponseDtoMapper.map(user);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/users/current")
    public ResponseEntity<UserResponseDto> updateCurrentUserInfo(
            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
            @RequestBody @Valid UpdateUserDto updateUserDto) {

        User user = userService.updateUserInfoById(userId, updateUserDto);
        UserResponseDto responseDto = userResponseDtoMapper.map(user);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/users/current")
    public ResponseEntity<Void> deleteCurrentUser(
            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId) {

        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/users/current/recover")
    public ResponseEntity<Void> recoverCurrentUser(
            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId) {

        userService.recoverUserById(userId);
        return ResponseEntity.ok().build();
    }
}
