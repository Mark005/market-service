package com.bmo.common.market_service.model.category;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {

  private UUID id;

  private String name;

  private String description;

  private String parentCategoryId;

  private List<CategoryResponseDto> subCategories;

}
