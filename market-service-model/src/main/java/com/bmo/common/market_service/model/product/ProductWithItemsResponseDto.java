package com.bmo.common.market_service.model.product;

import com.bmo.common.market_service.model.category.CategoryResponseDto;
import com.bmo.common.market_service.model.product_item.ProductItemResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ProductWithItemsResponseDto {

  private UUID id;

  private String name;

  private String description;

  private BigDecimal price;

  private Integer quantity;

  private String barcode;

  private List<CategoryResponseDto> categories;

  private List<ProductItemResponseDto> productItems;

}