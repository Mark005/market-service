package com.bmo.common.market_service.model.order_history;

import com.bmo.common.market_service.model.enums.PaymentStatusDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class PaymentDetailsSnapshotResponseDto {

    private UUID id;

    private BigDecimal amount;

    private String provider;

    private PaymentStatusDto paymentStatus;
}
