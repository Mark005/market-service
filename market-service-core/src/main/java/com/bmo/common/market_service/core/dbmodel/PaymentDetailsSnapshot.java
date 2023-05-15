package com.bmo.common.market_service.core.dbmodel;

import com.bmo.common.market_service.core.dbmodel.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class PaymentDetailsSnapshot {

    private UUID id;

    private BigDecimal amount;

    private String provider;

    private PaymentStatus paymentStatus;
}
