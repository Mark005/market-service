package com.bmo.common.market_service.model.cart;

import com.bmo.common.market_service.model.product.ProductSimpleResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CartProductResponseDto {

  private Integer quantity;

  private ProductSimpleResponseDto product;
}
