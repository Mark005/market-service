package com.bmo.common.market_service.model.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class CategoryCreateDto {

    private String name;

    private String description;

    private UUID parentCategoryId;

}
