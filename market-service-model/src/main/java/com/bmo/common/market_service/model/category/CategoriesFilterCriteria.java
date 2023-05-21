package com.bmo.common.market_service.model.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoriesFilterCriteria {

    private String name;
}
