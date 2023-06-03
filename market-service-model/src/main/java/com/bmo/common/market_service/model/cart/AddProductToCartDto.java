package com.bmo.common.market_service.model.cart;

import java.util.UUID;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddProductToCartDto {

  private UUID productId;

  @Min(1)
  private Integer quantity;

}
