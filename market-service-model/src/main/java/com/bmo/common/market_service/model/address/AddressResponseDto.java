package com.bmo.common.market_service.model.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class AddressResponseDto {

  private UUID id;

  private String country;

  private String city;

  private String street;

  private String building;

  private String apartment;

  private String postalCode;

  private String comment;

  private Boolean isPrimary;

}
