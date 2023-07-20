package com.bmo.common.market_service.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bmo.common.market_service.core.dbmodel.Cart;
import com.bmo.common.market_service.core.dbmodel.CartProduct;
import com.bmo.common.market_service.core.repository.CartProductRepository;
import com.bmo.common.market_service.core.repository.CartRepository;
import com.bmo.common.market_service.model.cart.AddProductToCartDto;
import com.bmo.common.market_service.model.exception.EntityNotFoundException;
import com.bmo.common.market_service.model.exception.MarketServiceBusinessException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {

  @Mock
  private CartRepository cartRepository;

  @Mock
  private CartProductRepository cartProductRepository;

  @InjectMocks
  private CartServiceImpl cartService;

  @Test
  public void testGetCartByUserId_CartExists_ReturnsCart() {
    UUID userId = UUID.randomUUID();
    Cart cart = new Cart();

    when(cartRepository.getCartByUserId(userId)).thenReturn(Optional.of(cart));

    Cart result = cartService.getCartByUserId(userId);

    assertEquals(cart, result);
    verify(cartRepository).getCartByUserId(userId);
  }

  @Test
  public void testGetCartByUserId_CartDoesNotExist_ThrowsEntityNotFoundException() {
    UUID userId = UUID.randomUUID();

    when(cartRepository.getCartByUserId(userId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> cartService.getCartByUserId(userId));

    verify(cartRepository).getCartByUserId(userId);
  }

  @Test
  public void testAddProductsToUsersCart_CartProductExists_SuccessfullyUpdatedQuantity() {
    UUID userId = UUID.randomUUID();
    UUID productId = UUID.randomUUID();
    int quantityToAdd = 5;

    CartProduct existingCartProduct = new CartProduct();
    existingCartProduct.setQuantity(10);

    when(cartProductRepository.findByCartUserIdAndProductId(userId, productId))
        .thenReturn(Optional.of(existingCartProduct));

    AddProductToCartDto productsDto = new AddProductToCartDto(productId, quantityToAdd);
    cartService.addProductsToUsersCart(userId, productsDto);

    assertEquals(15, existingCartProduct.getQuantity());
    verify(cartProductRepository).findByCartUserIdAndProductId(userId, productId);
    verify(cartProductRepository).save(existingCartProduct);
  }

  @Test
  public void testAddProductsToUsersCart_CartProductDoesNotExist_CreatesNewCartProduct() {
    UUID userId = UUID.randomUUID();
    UUID productId = UUID.randomUUID();
    int quantityToAdd = 7;

    when(cartProductRepository.findByCartUserIdAndProductId(userId, productId)).thenReturn(Optional.empty());
    when(cartRepository.getCartByUserId(userId)).thenReturn(Optional.of(new Cart()));

    AddProductToCartDto productsDto = new AddProductToCartDto(productId, quantityToAdd);
    cartService.addProductsToUsersCart(userId, productsDto);

    verify(cartProductRepository).findByCartUserIdAndProductId(userId, productId);
    verify(cartProductRepository).save(
        argThat(cartProduct -> cartProduct.getQuantity() == quantityToAdd));
  }

  @Test
  public void testRemoveProductsFromUsersCart_CartProductExists_SuccessfullyUpdatedQuantity() {
    UUID userId = UUID.randomUUID();
    UUID productId = UUID.randomUUID();
    int initialQuantity = 10;
    int quantityToRemove = 5;

    CartProduct existingCartProduct = new CartProduct();
    existingCartProduct.setQuantity(initialQuantity);

    when(cartProductRepository.findByCartUserIdAndProductId(userId, productId)).thenReturn(Optional.of(existingCartProduct));

    AddProductToCartDto productsDto = new AddProductToCartDto(productId, quantityToRemove);
    cartService.removeProductsFromUsersCart(userId, productsDto);

    assertEquals(initialQuantity - quantityToRemove, existingCartProduct.getQuantity());
    verify(cartProductRepository).findByCartUserIdAndProductId(userId, productId);
    verify(cartProductRepository).save(existingCartProduct);
    verify(cartProductRepository, never()).delete(any());
  }

  @Test
  public void testRemoveProductsFromUsersCart_CartProductDoesNotExist_ReturnsNoError() {
    UUID userId = UUID.randomUUID();
    UUID productId = UUID.randomUUID();
    int quantityToRemove = 7;

    when(cartProductRepository.findByCartUserIdAndProductId(userId, productId)).thenReturn(Optional.empty());

    AddProductToCartDto productsDto = new AddProductToCartDto(productId, quantityToRemove);
    cartService.removeProductsFromUsersCart(userId, productsDto);

    verify(cartProductRepository).findByCartUserIdAndProductId(userId, productId);
    verify(cartProductRepository, never()).save(any());
    verify(cartProductRepository, never()).delete(any());
  }

  @Test
  public void testRemoveProductsFromUsersCart_QuantityBecomesNegative_ThrowsMarketServiceBusinessException() {
    UUID userId = UUID.randomUUID();
    UUID productId = UUID.randomUUID();
    int initialQuantity = 2;
    int quantityToRemove = 5;

    CartProduct existingCartProduct = new CartProduct();
    existingCartProduct.setQuantity(initialQuantity);

    when(cartProductRepository.findByCartUserIdAndProductId(userId, productId)).thenReturn(Optional.of(existingCartProduct));

    AddProductToCartDto productsDto = new AddProductToCartDto(productId, quantityToRemove);
    assertThrows(MarketServiceBusinessException.class,
        () -> cartService.removeProductsFromUsersCart(userId, productsDto));
    verify(cartProductRepository).findByCartUserIdAndProductId(userId, productId);
    verify(cartProductRepository, never()).save(any());
    verify(cartProductRepository, never()).delete(any());
  }

  @Test
  public void testRemoveProductsFromUsersCart_QuantityBecomesZero_SuccessfullyDeletesCartProduct() {
    UUID userId = UUID.randomUUID();
    UUID productId = UUID.randomUUID();
    int initialQuantity = 2;
    int quantityToRemove = 2;

    CartProduct existingCartProduct = new CartProduct();
    existingCartProduct.setQuantity(initialQuantity);

    when(cartProductRepository.findByCartUserIdAndProductId(userId, productId)).thenReturn(Optional.of(existingCartProduct));

    AddProductToCartDto productsDto = new AddProductToCartDto(productId, quantityToRemove);
    cartService.removeProductsFromUsersCart(userId, productsDto);

    verify(cartProductRepository).findByCartUserIdAndProductId(userId, productId);
    verify(cartProductRepository).delete(existingCartProduct);
    verify(cartProductRepository, never()).save(any());
  }

  @Test
  public void testRemoveAllProductsFromUsersCart() {
    UUID userId = UUID.randomUUID();

    cartService.removeAllProductsFromUsersCart(userId);

    verify(cartProductRepository).deleteAllByCartUserId(userId);
  }
}