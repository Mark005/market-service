package com.bmo.common.market_service.core.controller;

import com.bmo.common.gateway.header.GatewayHeader;
import com.bmo.common.market_service.core.dbmodel.PaymentDetails;
import com.bmo.common.market_service.core.mapper.PaymentDetailsMapper;
import com.bmo.common.market_service.core.service.PaymentDetailsService;
import com.bmo.common.market_service.model.payment_details.MakePaymentRequestDto;
import com.bmo.common.market_service.model.payment_details.PaymentDetailsResponseDto;
import com.bmo.common.market_service.model.payment_details.PaymentStatusChangeRequestDto;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentDetailsController {

    private final PaymentDetailsService paymentDetailsService;
    private final PaymentDetailsMapper paymentDetailsMapper;

    @PatchMapping("/users/current/payment-details/{id}/pay")
    public ResponseEntity<PaymentDetailsResponseDto> makePayment(
            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID currentUserId,
            @NotNull @PathVariable("id") UUID paymentId,
            @RequestBody MakePaymentRequestDto makePaymentRequestDto) {

        PaymentDetails paymentDetails = paymentDetailsService.makePayment(currentUserId, paymentId, makePaymentRequestDto);
        PaymentDetailsResponseDto responseDto = paymentDetailsMapper.mapToResponseDto(paymentDetails);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/users/current/payment-details/{id}/status")
    public ResponseEntity<PaymentDetailsResponseDto> updatePaymentStatus(
            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID currentUserId,
            @NotNull @PathVariable("id") UUID paymentId,
            @RequestBody PaymentStatusChangeRequestDto paymentStatusChangeRequestDto) {

        PaymentDetails paymentDetails = paymentDetailsService.changePaymentStatus(currentUserId, paymentId, paymentStatusChangeRequestDto);
        PaymentDetailsResponseDto responseDto = paymentDetailsMapper.mapToResponseDto(paymentDetails);
        return ResponseEntity.ok(responseDto);
    }
}
