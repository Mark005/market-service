package com.bmo.common.market_service.core.controller;

import com.bmo.common.gateway.header.GatewayHeader;
import com.bmo.common.market_service.core.dbmodel.Cart;
import com.bmo.common.market_service.core.mapper.cart.CartResponseDtoMapper;
import com.bmo.common.market_service.core.service.CartService;
import com.bmo.common.market_service.model.cart.CartResponseDto;
import com.bmo.common.market_service.model.cart.ProductsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartResponseDtoMapper cartResponseDtoMapper;

    @GetMapping("/carts/current")
    public ResponseEntity<CartResponseDto> getCartWithProducts(
            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId) {

        Cart cart = cartService.getCartByUserId(userId);
        CartResponseDto cartResponseDto = cartResponseDtoMapper.map(cart);
        return ResponseEntity.ok(cartResponseDto);
    }

    @PatchMapping("/carts/current/add-products")
    public ResponseEntity<CartResponseDto> addProductsToCart(
            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
            @RequestBody @Valid ProductsDto productsDto) {

        Cart cart = cartService.addProductsToUsersCart(userId, productsDto);
        CartResponseDto cartResponseDto = cartResponseDtoMapper.map(cart);
        return ResponseEntity.ok(cartResponseDto);
    }

    @PatchMapping("/carts/current/remove-products")
    public ResponseEntity<CartResponseDto> removeProductsFromCart(
            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
            @RequestBody @Valid ProductsDto productsDto) {

        Cart cart = cartService.removeProductsFromUsersCart(userId, productsDto);
        CartResponseDto cartResponseDto = cartResponseDtoMapper.map(cart);
        return ResponseEntity.ok(cartResponseDto);
    }

    @PatchMapping("/carts/current/clear")
    public ResponseEntity<CartResponseDto> removeAllProductsFromCart(
            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId) {

        Cart cart = cartService.removeAllProductsFromUsersCart(userId);
        CartResponseDto cartResponseDto = cartResponseDtoMapper.map(cart);
        return ResponseEntity.ok(cartResponseDto);
    }
}
