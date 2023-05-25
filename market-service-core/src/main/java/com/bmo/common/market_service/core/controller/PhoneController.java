package com.bmo.common.market_service.core.controller;

import com.bmo.common.gateway.header.GatewayHeader;
import com.bmo.common.market_service.core.dbmodel.Phone;
import com.bmo.common.market_service.core.mapper.PhoneMapper;
import com.bmo.common.market_service.core.service.PhoneService;
import com.bmo.common.market_service.model.phone.PhoneCreateDto;
import com.bmo.common.market_service.model.phone.PhoneResponseDto;
import com.bmo.common.market_service.model.phone.PhoneUpdateDto;
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
public class PhoneController {

    private final PhoneService phoneService;
    private final PhoneMapper phoneMapper;

    @GetMapping("/users/current/phones/{id}")
    public ResponseEntity<PhoneResponseDto> getPhoneForCurrentUserById(
            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
            @NotNull @PathVariable("id") UUID phoneId,
            @RequestBody @Valid PhoneUpdateDto updatedPhone) {

        Phone phone = phoneService.getPhoneByIdAndUserId(userId, phoneId, updatedPhone);
        PhoneResponseDto phoneResponseDto = phoneMapper.map(phone);
        return ResponseEntity.ok(phoneResponseDto);
    }

    @GetMapping("/users/current/phones")
    public ResponseEntity<List<PhoneResponseDto>> getAllPhones(
            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId) {

        List<Phone> phones = phoneService.getAllPhonesByUserId(userId);
        List<PhoneResponseDto> cartResponseDto = phoneMapper.map(phones);
        return ResponseEntity.ok(cartResponseDto);
    }

    @PostMapping("/users/current/phones")
    public ResponseEntity<PhoneResponseDto> addPhone(
            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
            @RequestBody @Valid PhoneCreateDto newPhone) {

        Phone phone = phoneService.addPhoneToUser(userId, newPhone);
        PhoneResponseDto phoneResponseDto = phoneMapper.map(phone);
        return ResponseEntity.ok(phoneResponseDto);
    }

    @PutMapping("/users/current/phones/{id}")
    public ResponseEntity<PhoneResponseDto> updatePhone(
            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
            @NotNull @PathVariable("id") UUID phoneId,
            @RequestBody @Valid PhoneUpdateDto updatedPhone) {

        Phone phone = phoneService.updateUsersPhone(userId, phoneId, updatedPhone);
        PhoneResponseDto phoneResponseDto = phoneMapper.map(phone);
        return ResponseEntity.ok(phoneResponseDto);
    }

    @DeleteMapping("/users/current/phones/{id}")
    public ResponseEntity<Void> deletePhone(
            @NotNull @RequestHeader(GatewayHeader.USER_ID) UUID userId,
            @NotNull @PathVariable("id") UUID phoneId) {

        phoneService.deleteUsersPhone(userId, phoneId);
        return ResponseEntity.noContent().build();
    }

}
