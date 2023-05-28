package com.bmo.common.market_service.model.product_item;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductItemCreateDto {

  private UUID productId;

  private Integer itemsCount;
}
