package com.bmo.common.market_service.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bmo.common.market_service.core.dbmodel.ProductItem;
import com.bmo.common.market_service.core.dbmodel.enums.ProductItemStatus;
import com.bmo.common.market_service.core.mapper.EnumMapper;
import com.bmo.common.market_service.core.mapper.EnumMapperImpl;
import com.bmo.common.market_service.core.mapper.PageableMapper;
import com.bmo.common.market_service.core.repository.ProductItemRepository;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.enums.ProductItemStatusDto;
import com.bmo.common.market_service.model.exception.EntityNotFoundException;
import com.bmo.common.market_service.model.product_item.ProductItemCreateDto;
import com.bmo.common.market_service.model.product_item.ProductItemFiltersCriteria;
import com.bmo.common.market_service.model.product_item.ProductItemPatchStatusDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class ProductItemServiceImplTest {

  @Mock
  private ProductItemRepository productItemRepository;

  @Spy
  private EnumMapper enumMapper = new EnumMapperImpl();

  @InjectMocks
  private ProductItemServiceImpl productItemService;

  @Test
  public void testCreateProductItems_ValidProductCreateDto_CreatesAndSavesProductItems() {
    int itemsCount = 5;
    UUID productId = UUID.randomUUID();

    ProductItemCreateDto productCreateDto = new ProductItemCreateDto();
    productCreateDto.setItemsCount(itemsCount);
    productCreateDto.setProductId(productId);

    when(productItemRepository.saveAll(anyList()))
        .then(AdditionalAnswers.returnsFirstArg());

    List<ProductItem> result = productItemService.createProductItems(productCreateDto);

    assertNotNull(result);
    assertEquals(itemsCount, result.size());
    assertTrue(result.stream().allMatch(item ->
        item.getStatus() == ProductItemStatus.AVAILABLE &&
            item.getProduct().getId().equals(productId))
    );

    verify(productItemRepository).saveAll(anyList());
  }

  @Test
  public void testGetProductItemsByProductId_ValidProductIdAndFilters_ReturnsProductItems() {
    UUID productId = UUID.randomUUID();
    ProductItemFiltersCriteria filtersCriteria = new ProductItemFiltersCriteria();
    filtersCriteria.setItemStatus(ProductItemStatusDto.AVAILABLE);
    PageRequestDto pageRequest = new PageRequestDto(10, 1);

    List<ProductItem> productItems = List.of(new ProductItem(), new ProductItem());
    Page<ProductItem> expectedPage = new PageImpl<>(productItems, PageRequest.of(1, 10),
        productItems.size());

    when(enumMapper.map(filtersCriteria.getItemStatus())).thenReturn(ProductItemStatus.AVAILABLE);
    when(productItemRepository.findAll(any(Specification.class),
        eq(PageableMapper.map(pageRequest))))
        .thenReturn(expectedPage);

    Page<ProductItem> result = productItemService.getProductItemsByProductId(productId,
        filtersCriteria, pageRequest);

    assertNotNull(result);
    assertEquals(expectedPage, result);

    verify(productItemRepository)
        .findAll(any(Specification.class), eq(PageableMapper.map(pageRequest)));
  }

  @Test
  public void testGetProductItemById_ProductItemExists_ReturnsProductItem() {
    UUID productItemId = UUID.randomUUID();
    ProductItem productItemFromDb = new ProductItem();

    when(productItemRepository.findById(productItemId)).thenReturn(Optional.of(productItemFromDb));

    ProductItem result = productItemService.getProductItemById(productItemId);

    assertEquals(productItemFromDb, result);

    verify(productItemRepository).findById(productItemId);
  }

  @Test
  public void testGetProductItemById_ProductItemDoesNotExist_ThrowsEntityNotFoundException() {
    UUID productItemId = UUID.randomUUID();

    when(productItemRepository.findById(productItemId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class,
        () -> productItemService.getProductItemById(productItemId));

    verify(productItemRepository).findById(productItemId);
  }

  @Test
  public void testPatchProductItemStatus_ProductItemExists_StatusUpdatedSuccessfully() {
    UUID productItemId = UUID.randomUUID();
    ProductItemPatchStatusDto patchStatusDto = new ProductItemPatchStatusDto(
        ProductItemStatusDto.AVAILABLE);

    ProductItem productItemFromDb = new ProductItem();

    when(enumMapper.map(patchStatusDto.getStatus())).thenReturn(ProductItemStatus.AVAILABLE);
    when(productItemRepository.findById(productItemId)).thenReturn(Optional.of(productItemFromDb));
    when(productItemRepository.save(productItemFromDb)).thenReturn(productItemFromDb);

    ProductItem result = productItemService.patchProductItemStatus(productItemId, patchStatusDto);

    assertNotNull(result);
    assertEquals(ProductItemStatus.AVAILABLE, result.getStatus());

    verify(productItemRepository).findById(productItemId);
    verify(productItemRepository).save(productItemFromDb);
  }

  @Test
  public void testPatchProductItemStatus_ProductItemDoesNotExist_ThrowsEntityNotFoundException() {
    UUID productItemId = UUID.randomUUID();
    ProductItemPatchStatusDto patchStatusDto = new ProductItemPatchStatusDto(
        ProductItemStatusDto.AVAILABLE);

    when(enumMapper.map(patchStatusDto.getStatus())).thenReturn(ProductItemStatus.AVAILABLE);
    when(productItemRepository.findById(productItemId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class,
        () -> productItemService.patchProductItemStatus(productItemId, patchStatusDto));

    verify(productItemRepository).findById(productItemId);
    verify(productItemRepository, never()).save(any());
  }

  @Test
  public void testDeleteProductItem_ProductItemExists_DeletesProductItem() {
    UUID productItemId = UUID.randomUUID();

    productItemService.deleteProductItem(productItemId);

    verify(productItemRepository).deleteById(productItemId);
  }

}