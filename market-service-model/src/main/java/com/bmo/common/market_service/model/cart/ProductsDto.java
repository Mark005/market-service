package com.bmo.common.market_service.model.cart;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductsDto {

  private List<UUID> productIds;

}
