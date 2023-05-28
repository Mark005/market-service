package com.bmo.common.market_service.model.phone;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PhoneResponseDto {

  private UUID id;

  private String number;

  private Boolean isPrimary;
}
