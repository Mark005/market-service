package com.bmo.common.market_service.core.dbmodel;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductSnapshot {

  private String name;

  private String description;

  private BigDecimal price;

  private String barcode;

  private Integer quantity;
}
