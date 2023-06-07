package com.bmo.common.market_service.model.users_oreder;

import com.bmo.common.market_service.model.enums.OrderStatusDto;
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

  private OrderStatusDto status;

  private ZonedDateTime orderDateTime;

  private ZonedDateTime lastUpdateDateTime;

  private OrderInfoDto orderInfo;

  private List<ProductItemWithProductResponseDto> productItems;

  private PaymentDetailsResponseDto paymentDetails;
}
