package com.bmo.common.market_service.core.dbmodel;

import com.bmo.common.market_service.core.dbmodel.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class OrderDetailsSnapshot {

    private UUID id;
    private OrderStatus orderStatus;
    private ZonedDateTime orderDate;
    private ZonedDateTime lastUpdateDate;
    private PaymentDetailsSnapshot paymentDetailsSnapshot;
}
