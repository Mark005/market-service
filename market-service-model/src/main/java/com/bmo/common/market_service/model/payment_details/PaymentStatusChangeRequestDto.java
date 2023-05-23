package com.bmo.common.market_service.model.payment_details;

import com.bmo.common.market_service.model.enums.PaymentMethodDto;
import com.bmo.common.market_service.model.enums.PaymentStatusDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PaymentStatusChangeRequestDto {

    private PaymentStatusDto paymentMethod;
}
