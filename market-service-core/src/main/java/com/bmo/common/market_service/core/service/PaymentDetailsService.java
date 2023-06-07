package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.PaymentDetails;
import com.bmo.common.market_service.core.dbmodel.enums.PaymentStatus;
import com.bmo.common.market_service.model.payment_details.MakePaymentRequestDto;
import java.util.UUID;

public interface PaymentDetailsService {

  PaymentDetails getByOrderIdAndUserId(UUID orderId, UUID currentUserId);

  PaymentDetails makePayment(UUID paymentId,
      UUID currentUserId,
      MakePaymentRequestDto makePaymentRequestDto);

  PaymentDetails changePaymentStatus(UUID paymentId,
      UUID currentUserId,
      PaymentStatus newPaymentStatus);
}
