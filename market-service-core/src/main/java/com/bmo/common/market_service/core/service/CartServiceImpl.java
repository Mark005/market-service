package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Cart;
import com.bmo.common.market_service.core.dbmodel.CartProduct;
import com.bmo.common.market_service.core.dbmodel.Product;
import com.bmo.common.market_service.core.repository.CartProductRepository;
import com.bmo.common.market_service.core.repository.CartRepository;
import com.bmo.common.market_service.model.cart.AddProductToCartDto;
import com.bmo.common.market_service.model.exception.EntityNotFoundException;
import com.bmo.common.market_service.model.exception.MarketServiceBusinessException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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
  public void addProductsToUsersCart(UUID userId, AddProductToCartDto productsDto) {
    CartProduct cartProduct = cartProductRepository.findByCartUserIdAndProductId(userId,
            productsDto.getProductId())
        .orElseGet(() -> createNewCartProduct(userId, productsDto.getProductId()));
    int finalQuantity = cartProduct.getQuantity() + productsDto.getQuantity();
    cartProduct.setQuantity(finalQuantity);
    cartProductRepository.save(cartProduct);
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
  public void removeProductsFromUsersCart(UUID userId, AddProductToCartDto productsDto) {
    Optional<CartProduct> cartProductOpt = cartProductRepository.findByCartUserIdAndProductId(userId,
        productsDto.getProductId());
    if (cartProductOpt.isEmpty()) {
      return;
    }

    CartProduct cartProduct = cartProductOpt.get();
    int finalQuantity = cartProduct.getQuantity() - productsDto.getQuantity();

    if (finalQuantity < 0) {
      throw new MarketServiceBusinessException("Quantity of product in a cart can't be negative");
    }

    if (finalQuantity == 0) {
      cartProductRepository.delete(cartProduct);
      return;
    }

    cartProduct.setQuantity(finalQuantity);
    cartProductRepository.save(cartProduct);
  }

  @Override
  public void removeAllProductsFromUsersCart(UUID userId) {
    cartProductRepository.deleteAllByCartUserId(userId);
  }
}
