package com.bmo.common.market_service.model.product;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductUpdateDto {

  private String name;

  private String description;

  private BigDecimal price;

  private String barcode;

  private Set<UUID> categoryIds;

}
