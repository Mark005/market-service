package com.bmo.common.market_service.model.product_item;

import com.bmo.common.market_service.model.enums.ProductItemStatusDto;
import com.bmo.common.market_service.model.product.ProductResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ProductItemWithProductResponseDto {

    private UUID id;

    private ProductItemStatusDto status;

    private ProductResponseDto product;
}
