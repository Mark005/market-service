package com.bmo.common.market_service.model.order_history;

import com.bmo.common.market_service.model.enums.OrderStatusDto;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UsersOrderSnapshotResponseDto {

  private UUID id;

  private OrderStatusDto orderStatus;

  private ZonedDateTime orderDate;

  private ZonedDateTime lastUpdateDate;

  private PaymentDetailsSnapshotResponseDto paymentDetailsSnapshot;
}
