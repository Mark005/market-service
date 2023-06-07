package com.bmo.common.market_service.core.dbmodel;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderInfo {

  private List<ProductSnapshot> products;

  private BigDecimal productsPrice;
}
