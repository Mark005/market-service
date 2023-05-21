package com.bmo.common.market_service.core.mapper.category;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.Category;
import com.bmo.common.market_service.model.category.CategoryResponseDto;
import com.bmo.common.market_service.model.category.CategorySimpleResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapStructCommonConfig.class)
public interface CategoryResponseDtoMapper {
    List<CategoryResponseDto> map(List<Category> categories);

    @Mapping(target = "parentCategoryId", source = "parentCategory.id")
    CategorySimpleResponseDto map(Category category);
}
