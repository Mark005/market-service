package com.bmo.common.market_service.model.users_oreder;

import java.util.UUID;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemCreateDto {

  private UUID productId;

  @Min(1)
  private Integer itemsCount;
}
