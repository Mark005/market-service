package com.bmo.common.market_service.model.product_item;

import com.bmo.common.market_service.model.enums.ProductItemStatusDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductItemPatchStatusDto {

  private ProductItemStatusDto status;
}
