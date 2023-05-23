package com.bmo.common.market_service.model.payment_details;

import com.bmo.common.market_service.model.enums.PaymentMethodDto;
import com.bmo.common.market_service.model.enums.PaymentStatusDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class MakePaymentRequestDto {

    private PaymentMethodDto paymentMethod;
}
