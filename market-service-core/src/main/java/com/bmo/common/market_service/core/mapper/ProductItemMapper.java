package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.ProductItem;
import com.bmo.common.market_service.model.product_item.ProductItemResponseDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(config = MapStructCommonConfig.class)
public interface ProductItemMapper {

  List<ProductItemResponseDto> mapToResponseDto(List<ProductItem> productItems);

  ProductItemResponseDto mapToResponseDto(ProductItem productItem);
}
