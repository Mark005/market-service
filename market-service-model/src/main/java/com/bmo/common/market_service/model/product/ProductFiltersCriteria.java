package com.bmo.common.market_service.model.product;

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
public class ProductFiltersCriteria {

  private String name;

  private String description;

  private BigDecimal priceFrom;

  private BigDecimal priceTo;

  private String barcode;

  private List<UUID> categoryIds;

}
