package com.bmo.common.market_service.model.users_oreder;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoDto {

  private List<ProductSnapshotDto> products;

  private BigDecimal productsPrice;
}
