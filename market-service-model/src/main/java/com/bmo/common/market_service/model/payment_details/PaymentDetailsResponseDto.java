package com.bmo.common.market_service.model.payment_details;

import com.bmo.common.market_service.model.enums.PaymentMethodDto;
import com.bmo.common.market_service.model.enums.PaymentStatusDto;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetailsResponseDto {

  private UUID id;

  private BigDecimal amount;

  private PaymentMethodDto paymentMethod;

  private PaymentStatusDto paymentStatus;
}
