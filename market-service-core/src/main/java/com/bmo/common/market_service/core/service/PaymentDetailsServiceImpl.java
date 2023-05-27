package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.PaymentDetails;
import com.bmo.common.market_service.core.dbmodel.enums.PaymentStatus;
import com.bmo.common.market_service.model.exception.MarketServiceBusinessException;
import com.bmo.common.market_service.model.payment_details.MakePaymentRequestDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                                              PaymentStatus newPaymentStatus) {
        List<PaymentStatus> allowedStatuses = List.of(PaymentStatus.PAID, PaymentStatus.CANCELLED);
        if (!allowedStatuses.contains(newPaymentStatus)) {
            throw new MarketServiceBusinessException("Not allowed status [%s]".formatted(newPaymentStatus));
        }
        //ToDo
        return null;
    }
}
