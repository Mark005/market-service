package com.bmo.common.market_service.model.oreder_details;

import com.bmo.common.market_service.model.enums.OrderStatusDto;
import com.bmo.common.market_service.model.order_history.OrderHistoryResponseDto;
import com.bmo.common.market_service.model.payment_details.PaymentDetailsResponseDto;
import com.bmo.common.market_service.model.product_item.ProductItemWithProductResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class OrderDetailsResponseDto {

    private UUID id;

    private OrderStatusDto orderStatus;

    private ZonedDateTime orderDate;

    private ZonedDateTime lastUpdateDate;

    private List<ProductItemWithProductResponseDto> productItems;

    private PaymentDetailsResponseDto paymentDetails;

    private List<OrderHistoryResponseDto> orderHistories;
}