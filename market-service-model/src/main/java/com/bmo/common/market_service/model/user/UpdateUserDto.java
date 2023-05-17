package com.bmo.common.market_service.model.user;

import com.bmo.common.market_service.model.enums.GenderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class UpdateUserDto {

  @NotBlank
  private String name;

  @NotBlank
  private String surname;

  @NotBlank
  private String email;

  @NotNull
  private GenderDto gender;

}
