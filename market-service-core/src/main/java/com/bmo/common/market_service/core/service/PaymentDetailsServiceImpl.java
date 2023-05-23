package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.PaymentDetails;
import com.bmo.common.market_service.model.payment_details.MakePaymentRequestDto;
import com.bmo.common.market_service.model.payment_details.PaymentStatusChangeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentDetailsServiceImpl implements PaymentDetailsService {

    @Override
    public PaymentDetails makePayment(UUID currentUserId,
                                      UUID paymentId,
                                      MakePaymentRequestDto makePaymentRequestDto) {
        //ToDo
        return null;
    }

    @Override
    public PaymentDetails changePaymentStatus(UUID currentUserId,
                                              UUID paymentId,
                                              PaymentStatusChangeRequestDto paymentStatusChangeRequestDto) {
        //ToDo
        return null;
    }
}
