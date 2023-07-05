package com.bmo.common.market_service.model;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PageRequestDto {

  @NotNull
  private Integer pageSize;

  @NotNull
  private Integer pageNumber;
}
