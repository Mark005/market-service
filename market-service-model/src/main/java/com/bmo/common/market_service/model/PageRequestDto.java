package com.bmo.common.market_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PageRequestDto {

  private Integer pageSize;
  private Integer pageNumber;
}
