package com.bmo.common.market_service.model;

import com.bmo.common.market_service.model.enums.GenderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class RegisterUserResponseDto {
  private UUID id;
  private UUID securityUserId;
  private String name;
  private String surname;
  private String email;
  private GenderDto gender;
}
