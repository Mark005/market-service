package com.bmo.common.market_service.model.phone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PhoneCreateDto {

    private String number;

    private Boolean isPrimary;
}
