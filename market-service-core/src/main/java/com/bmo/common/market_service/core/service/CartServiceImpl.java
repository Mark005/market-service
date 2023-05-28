package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Cart;
import com.bmo.common.market_service.model.cart.ProductsDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  @Override
  public Cart getCartByUserId(UUID userId) {
    //ToDo
    return null;
  }

  @Override
  public Cart addProductsToUsersCart(UUID userId, ProductsDto productsDto) {
    //ToDo
    return null;
  }

  @Override
  public Cart removeProductsFromUsersCart(UUID userId, ProductsDto productsDto) {
    //ToDo
    return null;
  }

  @Override
  public Cart removeAllProductsFromUsersCart(UUID userId) {
    //ToDo
    return null;
  }
}
