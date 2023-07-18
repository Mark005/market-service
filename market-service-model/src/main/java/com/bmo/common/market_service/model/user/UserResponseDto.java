package com.bmo.common.market_service.model.user;

import com.bmo.common.market_service.model.enums.GenderDto;
import com.bmo.common.market_service.model.enums.UserStatusDto;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

  private UUID id;
  private UUID securityUserId;
  private String name;
  private String surname;
  private String email;
  private UserStatusDto status;
  private GenderDto gender;
}
