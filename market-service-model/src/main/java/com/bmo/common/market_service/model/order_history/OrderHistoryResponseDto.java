package com.bmo.common.market_service.model.order_history;

import com.bmo.common.market_service.model.enums.OrderStatusDto;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryResponseDto {

  private UUID id;

  private OrderStatusDto oldOrderStatus;

  private OrderStatusDto newOrderStatus;

  private ZonedDateTime date;

  private UsersOrderSnapshotResponseDto usersOrderSnapshot;
}
