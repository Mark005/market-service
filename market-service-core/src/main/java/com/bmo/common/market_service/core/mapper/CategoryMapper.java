package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.core.configs.MapStructCommonConfig;
import com.bmo.common.market_service.core.dbmodel.Category;
import com.bmo.common.market_service.model.category.CategoryCreateDto;
import com.bmo.common.market_service.model.category.CategoryResponseDto;
import com.bmo.common.market_service.model.category.CategorySimpleResponseDto;
import com.bmo.common.market_service.model.category.CategoryUpdateDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(config = MapStructCommonConfig.class, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface CategoryMapper {

  @Mapping(target = "parentCategoryId", source = "parentCategory.id")
  CategoryResponseDto mapToResponseDto(Category category);

  List<CategoryResponseDto> mapToResponseDto(List<Category> categories);

  @Mapping(target = "parentCategoryId", source = "parentCategory.id")
  CategorySimpleResponseDto mapToSimpleResponseDto(Category category);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "subCategories", ignore = true)
  @Mapping(target = "products", ignore = true)
  @Mapping(target = "parentCategory", expression = "java(mapParentCategory(newCategory))")
  Category mapFromCreateDto(CategoryCreateDto newCategory);

  default Category mapParentCategory(CategoryCreateDto newCategory) {
    if (newCategory == null || newCategory.getParentCategoryId() == null) {
      return null;
    }

    return Category.builder()
        .id(newCategory.getParentCategoryId())
        .build();
  }

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "subCategories", ignore = true)
  @Mapping(target = "products", ignore = true)
  Category merge(@MappingTarget Category categoryFromDb, CategoryUpdateDto categoryUpdateDto);
}
