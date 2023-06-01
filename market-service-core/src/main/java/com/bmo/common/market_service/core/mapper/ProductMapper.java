package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.Product;
import com.bmo.common.market_service.model.product.ProductCreateDto;
import com.bmo.common.market_service.model.product.ProductResponseDto;
import com.bmo.common.market_service.model.product.ProductUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructCommonConfig.class,
    uses = {CategoryMapper.class})
public interface ProductMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "carts", ignore = true)
  @Mapping(target = "productItems", ignore = true)
  @Mapping(target = "categories", source = "categoryIds")
  Product mapFromCreateDto(ProductCreateDto productCreateDto);

  ProductResponseDto mapToResponseDto(Product product);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "carts", ignore = true)
  @Mapping(target = "productItems", ignore = true)
  @Mapping(target = "categories", source = "categoryIds")
  Product merge(@MappingTarget Product productFromDb, ProductUpdateDto productUpdateDto);

}
