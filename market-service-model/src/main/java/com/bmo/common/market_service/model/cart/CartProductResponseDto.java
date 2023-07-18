package com.bmo.common.market_service.model.cart;

import com.bmo.common.market_service.model.product.ProductSimpleResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProductResponseDto {

  private Integer quantity;

  private ProductSimpleResponseDto product;
}
