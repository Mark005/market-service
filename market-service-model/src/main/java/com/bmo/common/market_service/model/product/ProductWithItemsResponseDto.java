package com.bmo.common.market_service.model.product;

import com.bmo.common.market_service.model.category.CategorySimpleResponseDto;
import com.bmo.common.market_service.model.product_item.ProductItemResponseDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithItemsResponseDto {

  private UUID id;

  private String name;

  private String description;

  private BigDecimal price;

  private String barcode;

  private List<CategorySimpleResponseDto> categories;

  private List<ProductItemResponseDto> productItems;

}
