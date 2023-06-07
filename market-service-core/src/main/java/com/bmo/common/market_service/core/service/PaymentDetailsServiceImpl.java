package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.PaymentDetails;
import com.bmo.common.market_service.core.dbmodel.UsersOrder;
import com.bmo.common.market_service.core.dbmodel.enums.OrderStatus;
import com.bmo.common.market_service.core.dbmodel.enums.PaymentStatus;
import com.bmo.common.market_service.core.mapper.EnumMapper;
import com.bmo.common.market_service.core.repository.PaymentDetailsRepository;
import com.bmo.common.market_service.core.repository.UsersOrderRepository;
import com.bmo.common.market_service.model.exception.MarketServiceBusinessException;
import com.bmo.common.market_service.model.payment_details.MakePaymentRequestDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentDetailsServiceImpl implements PaymentDetailsService {

  private final PaymentDetailsRepository paymentDetailsRepository;
  private final UsersOrderRepository usersOrderRepository;

  private final EnumMapper enumMapper;

  @Override
  public PaymentDetails getByOrderIdAndUserId(UUID orderId, UUID currentUserId) {
    return paymentDetailsRepository.findByUsersOrderIdAndUsersOrderUserId(orderId, currentUserId);
  }

  @Override
  public PaymentDetails makePayment(
      UUID paymentId,
      UUID currentUserId,
      MakePaymentRequestDto makePaymentRequestDto) {
    PaymentDetails paymentDetails = paymentDetailsRepository.findByIdAndUsersOrderUserId(paymentId, currentUserId);
    paymentDetails.setPaymentMethod(enumMapper.map(makePaymentRequestDto.getPaymentMethod()));
    paymentDetails.setPaymentStatus(PaymentStatus.PENDING);

    UsersOrder usersOrder = paymentDetails.getUsersOrder();
    usersOrder.setStatus(OrderStatus.PAYMENT_PENDING);
    usersOrderRepository.save(usersOrder);

    return paymentDetailsRepository.save(paymentDetails);
  }

  @Override
  public PaymentDetails changePaymentStatus(UUID paymentId,
      UUID currentUserId,
      PaymentStatus newPaymentStatus) {
    List<PaymentStatus> allowedStatuses = List.of(PaymentStatus.PAID, PaymentStatus.CANCELLED);
    if (!allowedStatuses.contains(newPaymentStatus)) {
      throw new MarketServiceBusinessException("Not allowed status [%s]".formatted(newPaymentStatus));
    }

    PaymentDetails paymentDetails = paymentDetailsRepository.findByIdAndUsersOrderUserId(paymentId, currentUserId);
    if (paymentDetails.getPaymentStatus() != PaymentStatus.PENDING) {
      throw new MarketServiceBusinessException("Payment status can be changed only for status 'PENDING'");
    }

    paymentDetails.setPaymentStatus(newPaymentStatus);

    UsersOrder usersOrder = paymentDetails.getUsersOrder();

    if (newPaymentStatus == PaymentStatus.PAID) {
      usersOrder.setStatus(OrderStatus.PAYED);
    }

    if (newPaymentStatus == PaymentStatus.CANCELLED) {
      usersOrder.setStatus(OrderStatus.PAYMENT_CANCELLED);
    }
    usersOrderRepository.save(usersOrder);

    return paymentDetailsRepository.save(paymentDetails);
  }
}
