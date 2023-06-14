package com.bmo.common.market_service.core.service;

import com.bmo.common.delivery_service.client.DeliveryServiceClient;
import com.bmo.common.delivery_service.model.rest.DeliveryStatusDto;
import com.bmo.common.delivery_service.model.rest.DeliveryStatusUpdateDto;
import com.bmo.common.market_service.core.dbmodel.PaymentDetails;
import com.bmo.common.market_service.core.dbmodel.UsersOrder;
import com.bmo.common.market_service.core.dbmodel.enums.OrderStatus;
import com.bmo.common.market_service.core.dbmodel.enums.PaymentStatus;
import com.bmo.common.market_service.core.mapper.EnumMapper;
import com.bmo.common.market_service.core.repository.PaymentDetailsRepository;
import com.bmo.common.market_service.core.repository.UsersOrderRepository;
import com.bmo.common.market_service.model.enums.PaymentStatusDto;
import com.bmo.common.market_service.model.exception.MarketServiceBusinessException;
import com.bmo.common.market_service.model.payment_details.MakePaymentRequestDto;
import com.bmo.common.market_service.model.payment_details.PaymentStatusChangeRequestDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentDetailsServiceImpl implements PaymentDetailsService {

  private final PaymentDetailsRepository paymentDetailsRepository;
  private final UsersOrderRepository usersOrderRepository;

  private final DeliveryServiceClient deliveryServiceClient;

  private final EnumMapper enumMapper;

  @Override
  public PaymentDetails getByOrderIdAndUserId(UUID orderId, UUID currentUserId) {
    return paymentDetailsRepository.findByUsersOrderIdAndUsersOrderUserId(orderId, currentUserId);
  }

  @Override
  public PaymentDetails makePayment(
      UUID paymentId,
      UUID userId,
      MakePaymentRequestDto makePaymentRequestDto) {
    PaymentDetails paymentDetails = paymentDetailsRepository.findByIdAndUsersOrderUserId(paymentId, userId);
    paymentDetails.setPaymentMethod(enumMapper.map(makePaymentRequestDto.getPaymentMethod()));
    paymentDetails.setPaymentStatus(PaymentStatus.PENDING);

    UsersOrder usersOrder = paymentDetails.getUsersOrder();
    usersOrder.setStatus(OrderStatus.PAYMENT_PENDING);
    usersOrderRepository.save(usersOrder);

    return paymentDetailsRepository.save(paymentDetails);
  }

  @Override
  public PaymentDetails changePaymentStatus(UUID paymentId,
      UUID userId,
      PaymentStatusChangeRequestDto newPaymentStatusDto) {
    PaymentStatus newPaymentStatus = enumMapper.map(newPaymentStatusDto.getPaymentStatusDto());

    List<PaymentStatus> allowedStatuses = List.of(PaymentStatus.PAID, PaymentStatus.CANCELLED);
    if (!allowedStatuses.contains(newPaymentStatus)) {
      throw new MarketServiceBusinessException("Not allowed status [%s]".formatted(newPaymentStatus));
    }

    PaymentDetails paymentDetails = paymentDetailsRepository.findByIdAndUsersOrderUserId(paymentId, userId);
    if (paymentDetails.getPaymentStatus() != PaymentStatus.PENDING) {
      throw new MarketServiceBusinessException("Payment status can be changed only for status 'PENDING'");
    }

    paymentDetails.setPaymentStatus(newPaymentStatus);

    UsersOrder usersOrder = paymentDetails.getUsersOrder();

    if (newPaymentStatus == PaymentStatus.PAID) {
      usersOrder.setStatus(OrderStatus.PAID);

      DeliveryStatusUpdateDto statusUpdateDto = DeliveryStatusUpdateDto.builder()
          .status(DeliveryStatusDto.PAID)
          .build();
      deliveryServiceClient.updateDeliveryStatus(userId, usersOrder.getId(), statusUpdateDto);
    }

    if (newPaymentStatus == PaymentStatus.CANCELLED) {
      usersOrder.setStatus(OrderStatus.PAYMENT_CANCELLED);

      DeliveryStatusUpdateDto statusUpdateDto = DeliveryStatusUpdateDto.builder()
          .status(DeliveryStatusDto.CANCELED)
          .build();
      deliveryServiceClient.updateDeliveryStatus(userId, usersOrder.getId(), statusUpdateDto);
    }
    usersOrderRepository.save(usersOrder);

    return paymentDetailsRepository.save(paymentDetails);
  }
}
