package com.bmo.common.market_service.core.controller;

import com.bmo.common.gateway.header.GatewayHeader;
import com.bmo.common.market_service.core.dbmodel.OrderDetails;
import com.bmo.common.market_service.core.mapper.OrderDetailsMapper;
import com.bmo.common.market_service.core.service.OrderDetailsService;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.oreder_details.OrderCreateDto;
import com.bmo.common.market_service.model.oreder_details.OrderDetailsResponseDto;
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
public class OrderDetailsController {

  private final OrderDetailsService orderDetailsService;
  private final OrderDetailsMapper orderDetailsMapper;

  @GetMapping("/users/current/orders")
  public ResponseEntity<Page<OrderDetailsResponseDto>> getOrdersForCurrentUser(
      @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
      UsersFilterCriteria usersFilterCriteria,
      PageRequestDto pageRequest) {

    Page<OrderDetails> orders = orderDetailsService.getOrdersFiltered(userId, usersFilterCriteria, pageRequest);
    Page<OrderDetailsResponseDto> responsePage = orders.map(orderDetailsMapper::mapToResponseDto);
    return ResponseEntity.ok(responsePage);
  }

  @GetMapping("/users/current/orders/{id}")
  public ResponseEntity<OrderDetailsResponseDto> getCurrentUsersOrderById(
      @NotNull @PathVariable("id") UUID orderId,
      @RequestHeader(GatewayHeader.USER_ID) UUID currentUserId) {

    OrderDetails order = orderDetailsService.getOrderByIdAndUserId(orderId, currentUserId);
    OrderDetailsResponseDto responseDto = orderDetailsMapper.mapToResponseDto(order);
    return ResponseEntity.ok(responseDto);
  }

  @PostMapping("/users/current/orders")
  public ResponseEntity<OrderDetailsResponseDto> createOrder(
      @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID currentUserId,
      @RequestBody @Valid OrderCreateDto orderCreateDto) {

    OrderDetails order = orderDetailsService.createOrder(currentUserId, orderCreateDto);
    OrderDetailsResponseDto responseDto = orderDetailsMapper.mapToResponseDto(order);
    return ResponseEntity.ok(responseDto);
  }

  @PatchMapping("/users/current/orders/{id}/cancel")
  public ResponseEntity<OrderDetailsResponseDto> updateOrderById(
      @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
      @NotNull @PathVariable("id") UUID orderId) {

    OrderDetails order = orderDetailsService.cancelOrder(userId, orderId);
    OrderDetailsResponseDto responseDto = orderDetailsMapper.mapToResponseDto(order);
    return ResponseEntity.ok(responseDto);
  }
}
