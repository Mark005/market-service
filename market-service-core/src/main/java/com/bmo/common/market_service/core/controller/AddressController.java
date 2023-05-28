package com.bmo.common.market_service.core.controller;

import com.bmo.common.gateway.header.GatewayHeader;
import com.bmo.common.market_service.core.dbmodel.Address;
import com.bmo.common.market_service.core.mapper.AddressMapper;
import com.bmo.common.market_service.core.service.AddressService;
import com.bmo.common.market_service.model.address.AddressCreateDto;
import com.bmo.common.market_service.model.address.AddressResponseDto;
import com.bmo.common.market_service.model.address.AddressUpdateDto;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddressController {

  private final AddressService addressService;
  private final AddressMapper addressMapper;

  @GetMapping("/users/current/addresses/{id}")
  public ResponseEntity<AddressResponseDto> getAddressForCurrentUserById(
      @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
      @NotNull @PathVariable("id") UUID addressId) {

    Address address = addressService.getAddressByIdAndUserId(userId, addressId);
    AddressResponseDto addressResponseDto = addressMapper.mapToResponseDto(address);
    return ResponseEntity.ok(addressResponseDto);
  }

  @GetMapping("/users/current/addresses")
  public ResponseEntity<List<AddressResponseDto>> getAllAddresses(
      @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId) {

    List<Address> addresses = addressService.getAllAddressesByUserId(userId);
    List<AddressResponseDto> cartResponseDto = addressMapper.mapToResponseDto(addresses);
    return ResponseEntity.ok(cartResponseDto);
  }

  @PostMapping("/users/current/addresses")
  public ResponseEntity<AddressResponseDto> addAddress(
      @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
      @RequestBody @Valid AddressCreateDto newAddress) {

    Address address = addressService.addAddressToUser(userId, newAddress);
    AddressResponseDto addressResponseDto = addressMapper.mapToResponseDto(address);
    return ResponseEntity.ok(addressResponseDto);
  }

  @PutMapping("/users/current/addresses/{id}")
  public ResponseEntity<AddressResponseDto> updateAddress(
      @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
      @NotNull @PathVariable("id") UUID addressId,
      @RequestBody @Valid AddressUpdateDto updatedAddress) {

    Address address = addressService.updateUsersAddress(userId, addressId, updatedAddress);
    AddressResponseDto addressResponseDto = addressMapper.mapToResponseDto(address);
    return ResponseEntity.ok(addressResponseDto);
  }

  @DeleteMapping("/users/current/addresses/{id}")
  public ResponseEntity<Void> deleteAddress(
      @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
      @NotNull @PathVariable("id") UUID addressId) {

    addressService.deleteUsersAddress(userId, addressId);
    return ResponseEntity.noContent().build();
  }

}
