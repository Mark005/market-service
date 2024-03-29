package com.bmo.common.market_service.model.product_item;

import com.bmo.common.market_service.model.enums.ProductItemStatusDto;
import com.bmo.common.market_service.model.product.ProductSimpleResponseDto;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductItemWithProductResponseDto {

  private UUID id;

  private ProductItemStatusDto status;

  private ProductSimpleResponseDto product;
}
