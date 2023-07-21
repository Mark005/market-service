package com.bmo.common.market_service.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bmo.common.market_service.core.dbmodel.Product;
import com.bmo.common.market_service.core.mapper.PageableMapper;
import com.bmo.common.market_service.core.mapper.ProductMapper;
import com.bmo.common.market_service.core.repository.ProductRepository;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.exception.EntityNotFoundException;
import com.bmo.common.market_service.model.product.ProductCreateDto;
import com.bmo.common.market_service.model.product.ProductFiltersCriteria;
import com.bmo.common.market_service.model.product.ProductUpdateDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private ProductMapper productMapper;

  @InjectMocks
  private ProductServiceImpl productService;

  @Test
  public void testCreateProduct_ValidProductCreateDto_ReturnsCreatedProduct() {
    ProductCreateDto productCreateDto = new ProductCreateDto();

    Product product = new Product();
    when(productMapper.mapFromCreateDto(productCreateDto)).thenReturn(product);
    when(productRepository.save(product)).thenReturn(product);

    Product result = productService.createProduct(productCreateDto);

    assertNotNull(result);
    assertEquals(product, result);

    verify(productMapper).mapFromCreateDto(productCreateDto);
    verify(productRepository).save(product);
  }

  @Test
  public void testGetProductsFiltered_ValidFilters_ReturnsFilteredProducts() {
    ProductFiltersCriteria filter = new ProductFiltersCriteria();
    PageRequestDto pageRequest = new PageRequestDto(1, 10);

    List<Product> products = List.of(new Product(), new Product());
    Page<Product> expectedPage = new PageImpl<>(products, PageableMapper.map(pageRequest),
        products.size());

    when(productRepository.findAll(any(Specification.class), eq(PageableMapper.map(pageRequest))))
        .thenReturn(expectedPage);

    Page<Product> result = productService.getProductsFiltered(filter, pageRequest);

    assertNotNull(result);
    assertEquals(expectedPage, result);

    verify(productRepository).findAll(any(Specification.class),
        eq(PageableMapper.map(pageRequest)));
  }

  @Test
  public void testGetProductById_ProductExists_ReturnsProduct() {
    UUID productId = UUID.randomUUID();
    Product productFromDb = new Product();

    when(productRepository.findById(productId)).thenReturn(Optional.of(productFromDb));

    Product result = productService.getProductById(productId);

    assertNotNull(result);
    assertEquals(productFromDb, result);

    verify(productRepository).findById(productId);
  }

  @Test
  public void testGetProductById_ProductDoesNotExist_ThrowsEntityNotFoundException() {
    UUID productId = UUID.randomUUID();

    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class,
        () -> productService.getProductById(productId));

    verify(productRepository).findById(productId);
  }

  @Test
  public void testUpdateProduct_ProductExists_ReturnsUpdatedProduct() {
    UUID productId = UUID.randomUUID();
    ProductUpdateDto productUpdateDto = new ProductUpdateDto();

    Product productFromDb = new Product();
    Product updatedProduct = new Product();

    when(productRepository.findById(productId)).thenReturn(Optional.of(productFromDb));
    when(productMapper.merge(productFromDb, productUpdateDto)).thenReturn(updatedProduct);
    when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

    Product result = productService.updateProduct(productId, productUpdateDto);

    assertNotNull(result);
    assertEquals(updatedProduct, result);

    verify(productRepository).findById(productId);
    verify(productMapper).merge(productFromDb, productUpdateDto);
    verify(productRepository).save(updatedProduct);
  }

  @Test
  public void testUpdateProduct_ProductDoesNotExist_ThrowsEntityNotFoundException() {
    UUID productId = UUID.randomUUID();
    ProductUpdateDto productUpdateDto = new ProductUpdateDto();

    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class,
        () -> productService.updateProduct(productId, productUpdateDto));

    verify(productRepository).findById(productId);
    verify(productMapper, never()).merge(any(), any());
    verify(productRepository, never()).save(any());
  }

  @Test
  public void testDeleteProduct_ProductExists_DeletesProduct() {
    UUID productId = UUID.randomUUID();

    productService.deleteProduct(productId);

    verify(productRepository).deleteById(productId);
  }

}