package com.bmo.common.market_service.core.controller;

import com.bmo.common.gateway.header.GatewayHeader;
import com.bmo.common.market_service.core.dbmodel.OrderDetails;
import com.bmo.common.market_service.core.mapper.OrderDetailsResponseDtoMapper;
import com.bmo.common.market_service.core.service.OrderDetailsService;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.oreder_details.OrderDetailsResponseDto;
import com.bmo.common.market_service.model.user.UsersFilterCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;
    private final OrderDetailsResponseDtoMapper orderDetailsResponseDtoMapper;

//    private final AuthServiceClient authServiceClient;

    @GetMapping("/users/current/orders")
    public ResponseEntity<Page<OrderDetailsResponseDto>> getOrdersForCurrentUser(
            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
            UsersFilterCriteria usersFilterCriteria,
            PageRequestDto pageRequest) {

        Page<OrderDetails> orders = orderDetailsService.getOrdersFiltered(userId, usersFilterCriteria, pageRequest);
        Page<OrderDetailsResponseDto> responseDto = orders.map(orderDetailsResponseDtoMapper::map);
        return ResponseEntity.ok(responseDto);
    }

//    @GetMapping("/orders/{id}")
//    public ResponseEntity<UserResponseDto> getCurrentUser(
//            @RequestHeader(GatewayHeader.USER_ID) UUID currentUserId) {
//
//        User user = userService.getUserById(currentUserId);
//        UserResponseDto responseDto = userResponseDtoMapper.map(user);
//        return ResponseEntity.ok(responseDto);
//        return ResponseEntity.ok(null);
//    }
//
//    @GetMapping("/users/{userId}")
//    public ResponseEntity<UserResponseDto> getUserById(
//            @NotNull @PathVariable("userId") UUID userId) {
//
//        User user = userService.getUserById(userId);
//        UserResponseDto responseDto = userResponseDtoMapper.map(user);
//        return ResponseEntity.ok(responseDto);
//        return ResponseEntity.ok(null);
//    }
//
//    @PostMapping("/users/register")
//    public ResponseEntity<RegisterUserResponseDto> registerUser(
//            @NotNull @RequestHeader(GatewayHeader.SECURITY_USER_ID) UUID securityUserId,
//            @RequestBody @Valid RegisterUserDto registerUserDto) {
//
//        User user = userService.registerUser(securityUserId, registerUserDto);
//        RegisterUserResponseDto responseDto = registerUserResponseDtoMapper.map(user);
//        return ResponseEntity.ok(responseDto);
//        return ResponseEntity.ok(null);
//    }
//
//    @PatchMapping("/users/current")
//    public ResponseEntity<UserResponseDto> updateCurrentUserInfo(
//            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
//            @RequestBody @Valid UpdateUserDto updateUserDto) {
//
//        User user = userService.updateUserInfoById(userId, updateUserDto);
//        UserResponseDto responseDto = userResponseDtoMapper.map(user);
//        return ResponseEntity.ok(responseDto);
//        return ResponseEntity.ok(null);
//    }
//
//    @PatchMapping("/users/current/delete")
//    public ResponseEntity<Void> deleteCurrentUser(
//            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId) {
//
//        userService.deleteUserById(userId);
//        return ResponseEntity.noContent().build();
//        return ResponseEntity.ok(null);
//    }
//
//    @PatchMapping("/users/current/recover")
//    public ResponseEntity<Void> recoverCurrentUser(
//            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId) {
//
//        userService.recoverUserById(userId);
//        return ResponseEntity.ok().build();
//        return ResponseEntity.ok(null);
//    }
}
