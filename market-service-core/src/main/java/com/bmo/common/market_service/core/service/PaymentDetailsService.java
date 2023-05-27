package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.PaymentDetails;
import com.bmo.common.market_service.core.dbmodel.enums.PaymentStatus;
import com.bmo.common.market_service.model.payment_details.MakePaymentRequestDto;
import java.util.UUID;

public interface PaymentDetailsService {
    PaymentDetails makePayment(UUID currentUserId,
                               UUID paymentId,
                               MakePaymentRequestDto makePaymentRequestDto);

    PaymentDetails changePaymentStatus(UUID currentUserId,
                                       UUID paymentId,
                                       PaymentStatus newPaymentStatus);
}
