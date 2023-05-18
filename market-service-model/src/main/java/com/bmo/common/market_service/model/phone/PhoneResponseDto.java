package com.bmo.common.market_service.model.phone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class PhoneResponseDto {

    private UUID id;

    private String number;

    private Boolean isPrimary;
}
