package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Phone;
import com.bmo.common.market_service.model.phone.PhoneCreateDto;
import com.bmo.common.market_service.model.phone.PhoneUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {

    @Override
    public List<Phone> getAllPhonesByUserId(UUID userId) {
        //ToDo
        return null;
    }

    @Override
    public Phone addPhoneToUser(UUID userId, PhoneCreateDto newPhone) {
        //ToDo
        return null;
    }

    @Override
    public Phone updateUsersPhone(UUID userId, UUID phoneId, PhoneUpdateDto updatedPhone) {
        //ToDo
        return null;
    }

    @Override
    public void deleteUsersPhone(UUID userId, UUID phoneId) {
        //ToDo
    }
}
