package com.bmo.common.market_service.model.product;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSimpleResponseDto {

  private UUID id;

  private String name;

  private String description;

  private BigDecimal price;

  private String barcode;

}
