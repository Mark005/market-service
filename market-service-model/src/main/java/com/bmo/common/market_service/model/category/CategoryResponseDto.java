package com.bmo.common.market_service.model.category;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoryResponseDto {

  private UUID id;

  private String name;

  private String description;

  private List<CategoryResponseDto> subCategories;

}
