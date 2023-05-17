package com.bmo.common.market_service.core.mapper;

import com.bmo.common.market_service.model.user.UsersFilterCriteria;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageableMapper {
    public static Pageable map(UsersFilterCriteria filter) {
        return PageRequest.of(filter.getPageNumber(), filter.getPageSize());
    }
}
