package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.model.PageRequestDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageableMapper {

  public static Pageable map(PageRequestDto pageRequest) {
    return PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize());
  }
}
