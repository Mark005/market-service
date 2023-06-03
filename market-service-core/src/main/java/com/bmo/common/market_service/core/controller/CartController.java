package com.bmo.common.market_service.core.controller;

import com.bmo.common.gateway.header.GatewayHeader;
import com.bmo.common.market_service.core.dbmodel.Cart;
import com.bmo.common.market_service.core.mapper.CartMapper;
import com.bmo.common.market_service.core.service.CartService;
import com.bmo.common.market_service.model.cart.AddProductToCartDto;
import com.bmo.common.market_service.model.cart.CartResponseDto;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;
  private final CartMapper cartMapper;

  @GetMapping("/users/current/cart")
  public ResponseEntity<CartResponseDto> getCartWithProducts(
      @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId) {

    Cart cart = cartService.getCartByUserId(userId);
    CartResponseDto cartResponseDto = cartMapper.mapToResponseDto(cart);
    return ResponseEntity.ok(cartResponseDto);
  }

  @PatchMapping("/users/current/cart/add-products")
  public ResponseEntity<CartResponseDto> addProductsToCart(
      @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
      @RequestBody @Valid AddProductToCartDto productsDto) {

    Cart cart = cartService.addProductsToUsersCart(userId, productsDto);
    CartResponseDto cartResponseDto = cartMapper.mapToResponseDto(cart);
    return ResponseEntity.ok(cartResponseDto);
  }

  @PatchMapping("/users/current/cart/remove-products")
  public ResponseEntity<CartResponseDto> removeProductsFromCart(
      @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
      @RequestBody @Valid AddProductToCartDto productsDto) {

    Cart cart = cartService.removeProductsFromUsersCart(userId, productsDto);
    CartResponseDto cartResponseDto = cartMapper.mapToResponseDto(cart);
    return ResponseEntity.ok(cartResponseDto);
  }

  @PatchMapping("/users/current/cart/clear")
  public ResponseEntity<CartResponseDto> removeAllProductsFromCart(
      @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId) {

    Cart cart = cartService.removeAllProductsFromUsersCart(userId);
    CartResponseDto cartResponseDto = cartMapper.mapToResponseDto(cart);
    return ResponseEntity.ok(cartResponseDto);
  }
}
