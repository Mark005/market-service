package com.bmo.common.market_service.model;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDto {

  @NotNull
  private Integer pageSize;

  @NotNull
  private Integer pageNumber;
}
