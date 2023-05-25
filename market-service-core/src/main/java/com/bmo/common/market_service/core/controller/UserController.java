package com.bmo.common.market_service.core.controller;

import com.bmo.common.gateway.header.GatewayHeader;
import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.core.mapper.UserMapper;
import com.bmo.common.market_service.core.service.UserService;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.user.RegisterUserDto;
import com.bmo.common.market_service.model.user.UpdateUserDto;
import com.bmo.common.market_service.model.user.UserResponseDto;
import com.bmo.common.market_service.model.user.UsersFilterCriteria;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/users")
    public ResponseEntity<Page<UserResponseDto>> getUsersFiltered(UsersFilterCriteria usersFilterCriteria, PageRequestDto pageRequest) {

        Page<User> users = userService.getUsersFiltered(usersFilterCriteria, pageRequest);
        Page<UserResponseDto> responseDto = users.map(userMapper::mapToResponseDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/users/current")
    public ResponseEntity<UserResponseDto> getCurrentUser(
            @RequestHeader(GatewayHeader.USER_ID) UUID currentUserId) {

        User user = userService.getUserById(currentUserId);
        UserResponseDto responseDto = userMapper.mapToResponseDto(user);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(
            @NotNull @PathVariable("userId") UUID userId) {

        User user = userService.getUserById(userId);
        UserResponseDto responseDto = userMapper.mapToResponseDto(user);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/users/register")
    public ResponseEntity<UserResponseDto> registerUser(
            @NotNull @RequestHeader(GatewayHeader.SECURITY_USER_ID) UUID securityUserId,
            @RequestBody @Valid RegisterUserDto registerUserDto) {

        User user = userService.registerUser(securityUserId, registerUserDto);
        UserResponseDto responseDto = userMapper.mapToResponseDto(user);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/users/current")
    public ResponseEntity<UserResponseDto> updateCurrentUserInfo(
            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
            @RequestBody @Valid UpdateUserDto updateUserDto) {

        User user = userService.updateUserInfoById(userId, updateUserDto);
        UserResponseDto responseDto = userMapper.mapToResponseDto(user);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/users/current/delete")
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
