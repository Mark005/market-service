package com.bmo.common.market_service.model.oreder_details;

import com.bmo.common.market_service.model.enums.OrderStatusDto;
import com.bmo.common.market_service.model.order_history.OrderHistoryResponseDto;
import com.bmo.common.market_service.model.payment_details.PaymentDetailsResponseDto;
import com.bmo.common.market_service.model.product_item.ProductItemWithProductResponseDto;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderResponseDto {

  private UUID id;

  private OrderStatusDto orderStatus;

  private ZonedDateTime orderDateTime;

  private ZonedDateTime lastUpdateDateTime;

  private List<ProductItemWithProductResponseDto> productItems;

  private PaymentDetailsResponseDto paymentDetails;

  private List<OrderHistoryResponseDto> orderHistories;
}
