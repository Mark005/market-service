package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Cart;
import com.bmo.common.market_service.model.cart.AddProductToCartDto;
import java.util.UUID;

public interface CartService {

  Cart getCartByUserId(UUID userId);

  void addProductsToUsersCart(UUID userId, AddProductToCartDto productsDto);

  void removeProductsFromUsersCart(UUID userId, AddProductToCartDto productsDto);

  void removeAllProductsFromUsersCart(UUID userId);

}
