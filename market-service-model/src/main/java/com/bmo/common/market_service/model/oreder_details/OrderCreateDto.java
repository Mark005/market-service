package com.bmo.common.market_service.model.oreder_details;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderCreateDto {

  private List<OrderProduct> orderProducts;

  private UUID deliveryAddressId;
}
