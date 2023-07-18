package com.bmo.common.market_service.model.users_oreder;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSnapshotDto {

  private String name;

  private String description;

  private BigDecimal price;

  private String barcode;

  private Integer quantity;
}
