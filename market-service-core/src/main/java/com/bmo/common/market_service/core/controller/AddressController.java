package com.bmo.common.market_service.core.controller;

import com.bmo.common.gateway.header.GatewayHeader;
import com.bmo.common.market_service.core.dbmodel.Address;
import com.bmo.common.market_service.core.mapper.address.AddressResponseDtoMapper;
import com.bmo.common.market_service.core.service.AddressService;
import com.bmo.common.market_service.model.address.AddressCreateDto;
import com.bmo.common.market_service.model.address.AddressResponseDto;
import com.bmo.common.market_service.model.address.AddressUpdateDto;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final AddressResponseDtoMapper addressResponseDtoMapper;

    @GetMapping("/address")
    public ResponseEntity<List<AddressResponseDto>> getAllAddresses(
            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId) {

        List<Address> addresses = addressService.getAllAddressesByUserId(userId);
        List<AddressResponseDto> cartResponseDto = addressResponseDtoMapper.map(addresses);
        return ResponseEntity.ok(cartResponseDto);
    }

    @PostMapping("/address")
    public ResponseEntity<AddressResponseDto> addAddress(
            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
            @RequestBody @Valid AddressCreateDto newAddress) {

        Address address = addressService.addAddressToUser(userId, newAddress);
        AddressResponseDto addressResponseDto = addressResponseDtoMapper.map(address);
        return ResponseEntity.ok(addressResponseDto);
    }

    @PutMapping("/address/{id}")
    public ResponseEntity<AddressResponseDto> addAddress(
            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
            @NotNull @PathVariable("id") UUID addressId,
            @RequestBody @Valid AddressUpdateDto updatedAddress) {

        Address address = addressService.updateUsersAddress(userId, addressId, updatedAddress);
        AddressResponseDto addressResponseDto = addressResponseDtoMapper.map(address);
        return ResponseEntity.ok(addressResponseDto);
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<AddressResponseDto> deleteAddress(
            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
            @NotNull @PathVariable("id") UUID addressId) {

        Address address = addressService.deleteUsersAddress(userId, addressId);
        AddressResponseDto addressResponseDto = addressResponseDtoMapper.map(address);
        return ResponseEntity.ok(addressResponseDto);
    }

}
