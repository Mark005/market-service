package com.bmo.common.market_service.model.category;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategorySimpleResponseDto {

  private UUID id;

  private String name;

  private String description;

  private UUID parentCategoryId;

}
