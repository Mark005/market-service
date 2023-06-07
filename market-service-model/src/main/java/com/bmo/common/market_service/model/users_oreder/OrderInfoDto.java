package com.bmo.common.market_service.model.users_oreder;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderInfoDto {

  private List<ProductSnapshotDto> products;

  private BigDecimal productsPrice;
}
