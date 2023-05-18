package com.bmo.common.market_service.model.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ProductsDto {

  private List<UUID> productIds;

}
