package com.bmo.common.market_service.model.phone;

import com.bmo.common.market_service.model.enums.PhoneTypeDto;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PhoneResponseDto {

  private UUID id;

  private String phoneNumber;

  private PhoneTypeDto type;

  private Boolean isPrimary;
}
