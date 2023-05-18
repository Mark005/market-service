package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Cart;
import com.bmo.common.market_service.model.cart.ProductsDto;

import java.util.UUID;

public interface CartService {

    Cart getCartByUserId(UUID userId);

    Cart addProductsToUsersCart(UUID userId, ProductsDto productsDto);

    Cart removeProductsFromUsersCart(UUID userId, ProductsDto productsDto);

    Cart removeAllProductsFromUsersCart(UUID userId);

}
