package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.Category;
import com.bmo.common.market_service.model.category.CategoryResponseDto;
import com.bmo.common.market_service.model.category.CategorySimpleResponseDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructCommonConfig.class)
public interface CategoryMapper {
    List<CategoryResponseDto> mapToResponseDto(List<Category> categories);

    @Mapping(target = "parentCategoryId", source = "parentCategory.id")
    CategorySimpleResponseDto mapToResponseDto(Category category);
}
