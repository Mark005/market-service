package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.ProductItem;
import com.bmo.common.market_service.model.product_item.ProductItemResponseDto;
import org.mapstruct.Mapper;

@Mapper(config = MapStructCommonConfig.class)
public interface ProductItemMapper {

    ProductItemResponseDto mapToResponseDto(ProductItem productItem);
}
