package com.bmo.common.market_service.model.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class CategoryResponseDto {

    private UUID id;

    private String name;

    private String description;

    private CategoryResponseDto parentCategory;

    private List<CategoryResponseDto> subCategories;

}
