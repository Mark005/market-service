package com.bmo.common.market_service.model.product_item;

import com.bmo.common.market_service.model.enums.ProductItemStatusDto;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductItemResponseDto {

  private UUID id;

  private ProductItemStatusDto status;
}
