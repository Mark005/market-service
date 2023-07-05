package com.bmo.common.market_service.core.controller;

import com.bmo.common.gateway.header.GatewayHeader;
import com.bmo.common.market_service.core.dbmodel.UsersOrder;
import com.bmo.common.market_service.core.mapper.UsersOrderMapper;
import com.bmo.common.market_service.core.service.UsersOrderService;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.user.UsersFilterCriteria;
import com.bmo.common.market_service.model.users_oreder.OrderCreateDto;
import com.bmo.common.market_service.model.users_oreder.OrderResponseDto;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsersOrderController {

  private final UsersOrderService usersOrderService;
  private final UsersOrderMapper usersOrderMapper;

  @GetMapping("/users/current/orders")
  public ResponseEntity<Page<OrderResponseDto>> getOrdersForCurrentUser(
      @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
      UsersFilterCriteria usersFilterCriteria,
      @Validated PageRequestDto pageRequest) {

    Page<UsersOrder> orders = usersOrderService.getOrdersFiltered(userId, usersFilterCriteria, pageRequest);
    Page<OrderResponseDto> responsePage = orders.map(usersOrderMapper::mapToResponseDto);
    return ResponseEntity.ok(responsePage);
  }

  @GetMapping("/users/current/orders/{id}")
  public ResponseEntity<OrderResponseDto> getCurrentUsersOrderById(
      @NotNull @PathVariable("id") UUID orderId,
      @RequestHeader(GatewayHeader.USER_ID) UUID currentUserId) {

    UsersOrder usersOrder = usersOrderService.getOrderByIdAndUserId(orderId, currentUserId);
    OrderResponseDto responseDto = usersOrderMapper.mapToResponseDto(usersOrder);
    return ResponseEntity.ok(responseDto);
  }

  @PostMapping("/users/current/orders")
  public ResponseEntity<OrderResponseDto> createOrder(
      @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID currentUserId,
      @RequestBody @Valid OrderCreateDto orderCreateDto) {

    UsersOrder usersOrder = usersOrderService.createOrder(currentUserId, orderCreateDto);
    OrderResponseDto responseDto = usersOrderMapper.mapToResponseDto(usersOrder);
    return ResponseEntity.ok(responseDto);
  }

  @PatchMapping("/users/current/orders/{id}/cancel")
  public ResponseEntity<OrderResponseDto> updateOrderById(
      @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
      @NotNull @PathVariable("id") UUID orderId) {

    UsersOrder usersOrder = usersOrderService.cancelOrder(userId, orderId);
    OrderResponseDto responseDto = usersOrderMapper.mapToResponseDto(usersOrder);
    return ResponseEntity.ok(responseDto);
  }
}
