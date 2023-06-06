package com.bmo.common.market_service.model.users_oreder;

import java.util.UUID;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderProduct {

  @NotNull
  private UUID productId;

  @Min(1)
  private Integer quantity;
}
