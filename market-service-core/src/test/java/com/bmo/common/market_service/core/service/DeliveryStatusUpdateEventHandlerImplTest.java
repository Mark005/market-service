package com.bmo.common.market_service.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bmo.common.delivery_service.model.kafka.DeliveryStatusDto;
import com.bmo.common.delivery_service.model.kafka.DeliveryStatusUpdateEvent;
import com.bmo.common.market_service.core.dbmodel.UsersOrder;
import com.bmo.common.market_service.core.dbmodel.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DeliveryStatusUpdateEventHandlerImplTest {

  @Mock
  private UsersOrderService usersOrderService;

  @InjectMocks
  private DeliveryStatusUpdateEventHandlerImpl deliveryStatusUpdateEventHandler;

  @Test
  public void testHandleUpdateEvent_DeliveryStatusDelivered_OrderStatusUpdated() {
    DeliveryStatusUpdateEvent updateEvent =
        DeliveryStatusUpdateEvent.builder()
            .status(DeliveryStatusDto.DELIVERED)
            .build();

    UsersOrder usersOrder = new UsersOrder();
    usersOrder.setStatus(OrderStatus.PAID);

    when(usersOrderService.getOrderById(updateEvent.getOrderId())).thenReturn(usersOrder);

    deliveryStatusUpdateEventHandler.handleUpdateEvent(updateEvent);

    assertEquals(OrderStatus.ORDERED, usersOrder.getStatus());
    verify(usersOrderService).getOrderById(updateEvent.getOrderId());
    verify(usersOrderService).saveAndUpdateHistory(usersOrder);
  }

}