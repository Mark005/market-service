package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.Product;
import com.bmo.common.market_service.model.product.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructCommonConfig.class)
public interface ProductMapper {

    @Mapping(target = "categories.parentCategoryId", source = "categories.parentCategory.id")
    ProductResponseDto mapToResponseDto(Product product);
}
