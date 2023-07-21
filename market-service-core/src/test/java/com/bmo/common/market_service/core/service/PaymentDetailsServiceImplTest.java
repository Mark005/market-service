package com.bmo.common.market_service.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bmo.common.delivery_service.client.DeliveryServiceClient;
import com.bmo.common.delivery_service.model.rest.DeliveryStatusDto;
import com.bmo.common.delivery_service.model.rest.DeliveryStatusUpdateDto;
import com.bmo.common.market_service.core.dbmodel.PaymentDetails;
import com.bmo.common.market_service.core.dbmodel.UsersOrder;
import com.bmo.common.market_service.core.dbmodel.enums.OrderStatus;
import com.bmo.common.market_service.core.dbmodel.enums.PaymentMethod;
import com.bmo.common.market_service.core.dbmodel.enums.PaymentStatus;
import com.bmo.common.market_service.core.mapper.EnumMapper;
import com.bmo.common.market_service.core.mapper.EnumMapperImpl;
import com.bmo.common.market_service.core.repository.PaymentDetailsRepository;
import com.bmo.common.market_service.core.repository.UsersOrderRepository;
import com.bmo.common.market_service.model.enums.PaymentMethodDto;
import com.bmo.common.market_service.model.enums.PaymentStatusDto;
import com.bmo.common.market_service.model.exception.EntityNotFoundException;
import com.bmo.common.market_service.model.exception.MarketServiceBusinessException;
import com.bmo.common.market_service.model.payment_details.MakePaymentRequestDto;
import com.bmo.common.market_service.model.payment_details.PaymentStatusChangeRequestDto;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PaymentDetailsServiceImplTest {

  @Mock
  private PaymentDetailsRepository paymentDetailsRepository;

  @Mock
  private UsersOrderRepository usersOrderRepository;

  @Mock
  private UsersOrderService usersOrderService;

  @Mock
  private DeliveryServiceClient deliveryServiceClient;

  @Spy
  private EnumMapper enumMapper = new EnumMapperImpl();

  @InjectMocks
  private PaymentDetailsServiceImpl paymentDetailsService;

  @Test
  public void testGetByOrderIdAndUserId_PaymentDetailsExist_ReturnsPaymentDetails() {
    UUID orderId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    PaymentDetails paymentDetails = new PaymentDetails();

    when(paymentDetailsRepository.findByUsersOrderIdAndUsersOrderUserId(orderId, userId))
        .thenReturn(Optional.of(paymentDetails));

    PaymentDetails result = paymentDetailsService.getByOrderIdAndUserId(orderId, userId);

    assertEquals(paymentDetails, result);

    verify(paymentDetailsRepository).findByUsersOrderIdAndUsersOrderUserId(orderId, userId);
  }

  @Test
  public void testGetByOrderIdAndUserId_PaymentDetailsDoNotExist_ThrowsEntityNotFoundException() {
    UUID orderId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    when(paymentDetailsRepository.findByUsersOrderIdAndUsersOrderUserId(orderId, userId))
        .thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class,
        () -> paymentDetailsService.getByOrderIdAndUserId(orderId, userId));

    verify(paymentDetailsRepository).findByUsersOrderIdAndUsersOrderUserId(orderId, userId);
  }

  @Test
  public void testMakePayment_PaymentDetailsExist_SuccessfullyMadePayment() {
    UUID paymentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    MakePaymentRequestDto makePaymentRequestDto = new MakePaymentRequestDto();
    makePaymentRequestDto.setPaymentMethod(PaymentMethodDto.CARD);

    UsersOrder usersOrder = new UsersOrder();
    PaymentDetails paymentDetails = new PaymentDetails();
    paymentDetails.setUsersOrder(usersOrder);

    when(paymentDetailsRepository.findByIdAndUsersOrderUserId(paymentId, userId))
        .thenReturn(Optional.of(paymentDetails));
    when(paymentDetailsRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

    PaymentDetails result = paymentDetailsService.makePayment(paymentId, userId,
        makePaymentRequestDto);

    assertNotNull(result);
    assertEquals(PaymentStatus.PENDING, result.getPaymentStatus());
    assertEquals(PaymentMethod.CARD, result.getPaymentMethod());
    assertEquals(OrderStatus.PAYMENT_PENDING, usersOrder.getStatus());

    verify(paymentDetailsRepository).findByIdAndUsersOrderUserId(paymentId, userId);
    verify(usersOrderRepository).save(usersOrder);
    verify(paymentDetailsRepository).save(paymentDetails);
  }

  @Test
  public void testMakePayment_PaymentDetailsDoNotExist_ThrowsEntityNotFoundException() {
    UUID paymentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    MakePaymentRequestDto makePaymentRequestDto = new MakePaymentRequestDto();

    when(paymentDetailsRepository.findByIdAndUsersOrderUserId(paymentId, userId))
        .thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class,
        () -> paymentDetailsService.makePayment(paymentId, userId, makePaymentRequestDto));

    verify(paymentDetailsRepository).findByIdAndUsersOrderUserId(paymentId, userId);
    verify(usersOrderRepository, never()).save(any());
    verify(paymentDetailsRepository, never()).save(any());
  }

  @Test
  public void testChangePaymentStatus_ValidPaymentStatus_PaymentStatusChangedToPaid() {
    UUID paymentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    PaymentStatusChangeRequestDto newPaymentStatusDto = new PaymentStatusChangeRequestDto(
        PaymentStatusDto.PAID);

    PaymentDetails paymentDetails = new PaymentDetails();
    UsersOrder usersOrder = new UsersOrder();

    paymentDetails.setPaymentStatus(PaymentStatus.PENDING);
    paymentDetails.setUsersOrder(usersOrder);

    when(paymentDetailsRepository.findByIdAndUsersOrderUserId(paymentId, userId))
        .thenReturn(Optional.of(paymentDetails));
    when(paymentDetailsRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

    PaymentDetails result = paymentDetailsService.changePaymentStatus(paymentId, userId,
        newPaymentStatusDto);

    assertNotNull(result);
    assertEquals(PaymentStatus.PAID, result.getPaymentStatus());

    verify(paymentDetailsRepository).findByIdAndUsersOrderUserId(paymentId, userId);
    verify(paymentDetailsRepository).save(paymentDetails);
    verify(usersOrderRepository).save(usersOrder);
    verify(deliveryServiceClient)
        .updateDeliveryStatus(
            userId,
            usersOrder.getId(),
            DeliveryStatusUpdateDto.builder()
                .status(DeliveryStatusDto.PAID)
                .build());
  }

  @Test
  public void testChangePaymentStatus_ValidPaymentStatus_PaymentStatusChangedToCancelled() {
    UUID paymentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    PaymentStatusChangeRequestDto newPaymentStatusDto = new PaymentStatusChangeRequestDto(
        PaymentStatusDto.CANCELLED);

    PaymentDetails paymentDetails = new PaymentDetails();
    paymentDetails.setPaymentStatus(PaymentStatus.PENDING);

    UsersOrder usersOrder = new UsersOrder();
    paymentDetails.setUsersOrder(usersOrder);

    when(paymentDetailsRepository.findByIdAndUsersOrderUserId(paymentId, userId))
        .thenReturn(Optional.of(paymentDetails));
    when(paymentDetailsRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

    PaymentDetails result = paymentDetailsService.changePaymentStatus(paymentId, userId,
        newPaymentStatusDto);

    assertNotNull(result);
    assertEquals(PaymentStatus.CANCELLED, result.getPaymentStatus());

    verify(paymentDetailsRepository).findByIdAndUsersOrderUserId(paymentId, userId);
    verify(paymentDetailsRepository).save(paymentDetails);
    verify(usersOrderService).cancelOrder(userId, usersOrder.getId());
    verify(deliveryServiceClient)
        .updateDeliveryStatus(
            userId,
            usersOrder.getId(),
            DeliveryStatusUpdateDto.builder()
                .status(DeliveryStatusDto.CANCELED)
                .build());
  }

  @Test
  public void testChangePaymentStatus_InvalidPaymentStatus_ThrowsMarketServiceBusinessException() {
    UUID paymentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    PaymentStatusChangeRequestDto newPaymentStatusDto = new PaymentStatusChangeRequestDto(
        PaymentStatusDto.PAID);

    PaymentDetails paymentDetails = new PaymentDetails();
    paymentDetails.setPaymentStatus(PaymentStatus.PAID);

    UsersOrder usersOrder = new UsersOrder();
    paymentDetails.setUsersOrder(usersOrder);

    when(paymentDetailsRepository.findByIdAndUsersOrderUserId(paymentId, userId))
        .thenReturn(Optional.of(paymentDetails));

    assertThrows(MarketServiceBusinessException.class,
        () -> paymentDetailsService.changePaymentStatus(paymentId, userId, newPaymentStatusDto));

    verify(paymentDetailsRepository).findByIdAndUsersOrderUserId(paymentId, userId);
    verify(usersOrderRepository, never()).save(any());
    verify(paymentDetailsRepository, never()).save(any());
    verify(usersOrderService, never()).cancelOrder(any(), any());
    verify(deliveryServiceClient, never()).updateDeliveryStatus(any(), any(), any());
  }

  @Test
  public void testChangePaymentStatus_InvalidCurrentPaymentStatus_ThrowsMarketServiceBusinessException() {
    UUID paymentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    PaymentStatusChangeRequestDto newPaymentStatusDto = new PaymentStatusChangeRequestDto(
        PaymentStatusDto.PAID);

    PaymentDetails paymentDetails = new PaymentDetails();
    paymentDetails.setPaymentStatus(PaymentStatus.PAID);

    UsersOrder usersOrder = new UsersOrder();
    paymentDetails.setUsersOrder(usersOrder);

    when(paymentDetailsRepository.findByIdAndUsersOrderUserId(paymentId, userId))
        .thenReturn(Optional.of(paymentDetails));

    assertThrows(MarketServiceBusinessException.class,
        () -> paymentDetailsService.changePaymentStatus(paymentId, userId, newPaymentStatusDto));
    verify(paymentDetailsRepository).findByIdAndUsersOrderUserId(paymentId, userId);
    verify(usersOrderRepository, never()).save(any());
    verify(paymentDetailsRepository, never()).save(any());
    verify(usersOrderService, never()).cancelOrder(any(), any());
    verify(deliveryServiceClient, never()).updateDeliveryStatus(any(), any(), any());
  }

  @Test
  public void testChangePaymentStatus_PaymentDetailsDoNotExist_ThrowsEntityNotFoundException() {
    UUID paymentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    PaymentStatusChangeRequestDto newPaymentStatusDto = new PaymentStatusChangeRequestDto(
        PaymentStatusDto.PAID);

    when(paymentDetailsRepository.findByIdAndUsersOrderUserId(paymentId, userId))
        .thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class,
        () -> paymentDetailsService.changePaymentStatus(paymentId, userId, newPaymentStatusDto));
    verify(paymentDetailsRepository).findByIdAndUsersOrderUserId(paymentId, userId);
    verify(usersOrderRepository, never()).save(any());
    verify(paymentDetailsRepository, never()).save(any());
    verify(usersOrderService, never()).cancelOrder(any(), any());
    verify(deliveryServiceClient, never()).updateDeliveryStatus(any(), any(), any());
  }
}