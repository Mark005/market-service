package com.bmo.common.market_service.model.payment_details;

import com.bmo.common.market_service.model.enums.PaymentStatusDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusChangeRequestDto {

  private PaymentStatusDto paymentStatusDto;
}
