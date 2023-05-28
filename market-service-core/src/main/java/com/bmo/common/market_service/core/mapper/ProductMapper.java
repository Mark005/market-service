package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.Product;
import com.bmo.common.market_service.model.product.ProductResponseDto;
import org.mapstruct.Mapper;

@Mapper(config = MapStructCommonConfig.class,
    uses = {CategoryMapper.class})
public interface ProductMapper {

  ProductResponseDto mapToResponseDto(Product product);
}
