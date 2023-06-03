package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Cart;
import com.bmo.common.market_service.core.dbmodel.CartProduct;
import com.bmo.common.market_service.core.dbmodel.Product;
import com.bmo.common.market_service.core.repository.CartProductRepository;
import com.bmo.common.market_service.core.repository.CartRepository;
import com.bmo.common.market_service.model.cart.AddProductToCartDto;
import com.bmo.common.market_service.model.exception.EntityNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;
  private final CartProductRepository cartProductRepository;

  @Override
  public Cart getCartByUserId(UUID userId) {
    return cartRepository.getCartByUserId(userId)
        .orElseThrow(() -> new EntityNotFoundException("Cart for user with id [%s] not found".formatted(userId)));
  }

  @Override
//  @Transactional
  public Cart addProductsToUsersCart(UUID userId, AddProductToCartDto productsDto) {
    CartProduct cartProduct = cartProductRepository.findByCartUserIdAndProductId(userId,
            productsDto.getProductId())
        .orElseGet(() -> createNewCartProduct(userId, productsDto.getProductId()));
    int finalQuantity = cartProduct.getQuantity() + productsDto.getQuantity();
    cartProduct.setQuantity(finalQuantity);
    CartProduct saved = cartProductRepository.save(cartProduct);
    return getCartByUserId(userId);
  }

  private CartProduct createNewCartProduct(UUID userId, UUID productId) {
    Cart cartByUserId = getCartByUserId(userId);
    return CartProduct.builder()
        .quantity(0)
        .cart(cartByUserId)
        .product(Product.builder().id(productId).build())
        .build();
  }

  @Override
  public Cart removeProductsFromUsersCart(UUID userId, AddProductToCartDto productsDto) {
    //ToDo
    return null;
  }

  @Override
  public Cart removeAllProductsFromUsersCart(UUID userId) {
    //ToDo
    return null;
  }
}
