package com.bmo.common.market_service.core.dbmodel;

import com.bmo.common.market_service.core.dbmodel.enums.OrderStatus;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UsersOrderSnapshot {

  private UUID id;
  private OrderStatus status;
  private ZonedDateTime orderDate;
  private ZonedDateTime lastUpdateDate;
  private PaymentDetailsSnapshot paymentDetailsSnapshot;
}
