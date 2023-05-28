package com.bmo.common.market_service.core.controller;

import com.bmo.common.market_service.core.dbmodel.ProductItem;
import com.bmo.common.market_service.core.dbmodel.enums.ProductItemStatus;
import com.bmo.common.market_service.core.mapper.EnumMapper;
import com.bmo.common.market_service.core.mapper.ProductItemMapper;
import com.bmo.common.market_service.core.service.ProductItemService;
import com.bmo.common.market_service.model.product_item.ProductItemCreateDto;
import com.bmo.common.market_service.model.product_item.ProductItemPatchStatusDto;
import com.bmo.common.market_service.model.product_item.ProductItemResponseDto;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductItemController {

  private final ProductItemService productItemService;
  private final ProductItemMapper productItemMapper;
  private final EnumMapper enumMapper;

  @PostMapping("/product-item")
  public ResponseEntity<ProductItemResponseDto> createProductItem(
      @RequestBody ProductItemCreateDto productCreateDto) {

    ProductItem productItem = productItemService.createProductItem(productCreateDto);
    ProductItemResponseDto responseDto = productItemMapper.mapToResponseDto(productItem);
    return ResponseEntity.ok(responseDto);
  }

  @GetMapping("/product-item/{id}")
  public ResponseEntity<ProductItemResponseDto> getProductItemById(
      @PathVariable("id") UUID productItemId) {

    ProductItem productItem = productItemService.getProductItemById(productItemId);
    ProductItemResponseDto productItemResponseDto = productItemMapper.mapToResponseDto(productItem);
    return ResponseEntity.ok(productItemResponseDto);
  }

  @PatchMapping("/product-item/{id}/status")
  public ResponseEntity<ProductItemResponseDto> patchProductItemStatus(
      @NotNull @PathVariable("id") UUID productItemId,
      @RequestBody ProductItemPatchStatusDto patchStatusDto) {

    ProductItemStatus productItemStatus = enumMapper.map(patchStatusDto.getStatus());
    ProductItem product = productItemService.patchProductItemStatus(productItemId, productItemStatus);
    ProductItemResponseDto productResponseDto = productItemMapper.mapToResponseDto(product);
    return ResponseEntity.ok(productResponseDto);
  }

  @DeleteMapping("/product-item/{id}")
  public ResponseEntity<Void> deleteProductItem(
      @NotNull @PathVariable("id") UUID productItemId) {

    productItemService.deleteProductItem(productItemId);
    return ResponseEntity.noContent().build();
  }

}
