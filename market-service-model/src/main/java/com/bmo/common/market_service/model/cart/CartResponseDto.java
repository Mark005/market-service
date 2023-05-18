package com.bmo.common.market_service.model.cart;

import com.bmo.common.market_service.model.product.ProductResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class CartResponseDto {

  private UUID id;

  private List<ProductResponseDto> products;

}
