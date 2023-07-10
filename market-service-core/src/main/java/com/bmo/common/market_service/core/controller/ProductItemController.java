package com.bmo.common.market_service.core.controller;

import com.bmo.common.gateway.header.GatewayHeader;
import com.bmo.common.market_service.core.dbmodel.ProductItem;
import com.bmo.common.market_service.core.mapper.ProductItemMapper;
import com.bmo.common.market_service.core.service.CommonUserValidator;
import com.bmo.common.market_service.core.service.ProductItemService;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.product_item.ProductItemCreateDto;
import com.bmo.common.market_service.model.product_item.ProductItemFiltersCriteria;
import com.bmo.common.market_service.model.product_item.ProductItemPatchStatusDto;
import com.bmo.common.market_service.model.product_item.ProductItemResponseDto;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductItemController {

  private final CommonUserValidator commonUserValidator;
  private final ProductItemService productItemService;
  private final ProductItemMapper productItemMapper;

  @PostMapping("/product-items")
  public ResponseEntity<List<ProductItemResponseDto>> createProductItems(
      @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID currentUserId,
      @RequestBody @Valid ProductItemCreateDto productCreateDto) {

    commonUserValidator.validateUserNotDeleted(currentUserId);
    List<ProductItem> productItem = productItemService.createProductItems(productCreateDto);
    List<ProductItemResponseDto> responseDto = productItemMapper.mapToResponseDto(productItem);
    return ResponseEntity.ok(responseDto);
  }

  @GetMapping("/product-items/{id}")
  public ResponseEntity<ProductItemResponseDto> getProductItemById(
      @PathVariable("id") UUID productItemId) {

    ProductItem productItem = productItemService.getProductItemById(productItemId);
    ProductItemResponseDto productItemResponseDto = productItemMapper.mapToResponseDto(productItem);
    return ResponseEntity.ok(productItemResponseDto);
  }

  @GetMapping("/product/{id}/product-items")
  public ResponseEntity<Page<ProductItemResponseDto>> getProductItemsByProductId(
      @PathVariable("id") UUID productId,
      ProductItemFiltersCriteria filtersCriteria,
      @Validated PageRequestDto pageRequest) {

    Page<ProductItemResponseDto> productItemsPage =
        productItemService.getProductItemsByProductId(productId, filtersCriteria, pageRequest)
            .map(productItemMapper::mapToResponseDto);
    return ResponseEntity.ok(productItemsPage);
  }

  @PatchMapping("/product-items/{id}/status")
  public ResponseEntity<ProductItemResponseDto> patchProductItemStatus(
      @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID currentUserId,
      @NotNull @PathVariable("id") UUID productItemId,
      @RequestBody ProductItemPatchStatusDto patchStatusDto) {

    commonUserValidator.validateUserNotDeleted(currentUserId);

    ProductItem product = productItemService.patchProductItemStatus(productItemId, patchStatusDto);
    ProductItemResponseDto productResponseDto = productItemMapper.mapToResponseDto(product);
    return ResponseEntity.ok(productResponseDto);
  }

  @DeleteMapping("/product-items/{id}")
  public ResponseEntity<Void> deleteProductItem(
      @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID currentUserId,
      @NotNull @PathVariable("id") UUID productItemId) {

    commonUserValidator.validateUserNotDeleted(currentUserId);

    productItemService.deleteProductItem(productItemId);
    return ResponseEntity.noContent().build();
  }

}
