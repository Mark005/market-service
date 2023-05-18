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
public class OrderDetailsSnapshotResponseDto {

    private UUID id;

    private OrderStatusDto orderStatus;

    private ZonedDateTime orderDate;

    private ZonedDateTime lastUpdateDate;

    private PaymentDetailsSnapshotResponseDto paymentDetailsSnapshot;
}
