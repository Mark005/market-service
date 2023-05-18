package com.bmo.common.market_service.model.order_history;

import com.bmo.common.market_service.model.enums.OrderStatusDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class OrderHistoryResponseDto {

    private UUID id;

    private OrderStatusDto oldOrderStatus;

    private OrderStatusDto newOrderStatus;

    private ZonedDateTime date;

    private OrderDetailsSnapshotResponseDto orderDetailsSnapshot;
}
