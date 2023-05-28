package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Phone;
import com.bmo.common.market_service.model.phone.PhoneCreateDto;
import com.bmo.common.market_service.model.phone.PhoneUpdateDto;

import java.util.List;
import java.util.UUID;

public interface PhoneService {

    Phone getPhoneByIdAndUserId(UUID userId, UUID phoneId);

    List<Phone> getAllPhonesByUserId(UUID userId);

    Phone addPhoneToUser(UUID userId, PhoneCreateDto newPhone);

    Phone updateUsersPhone(UUID userId, UUID phoneId, PhoneUpdateDto updatedPhone);

    void deleteUsersPhone(UUID userId, UUID phoneId);
}
