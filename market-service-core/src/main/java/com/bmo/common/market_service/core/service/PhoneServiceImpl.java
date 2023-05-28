package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Phone;
import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.core.mapper.PhoneMapper;
import com.bmo.common.market_service.core.repository.PhoneRepository;
import com.bmo.common.market_service.model.exception.EntityNotFoundException;
import com.bmo.common.market_service.model.exception.MarketServiceBusinessException;
import com.bmo.common.market_service.model.phone.PhoneCreateDto;
import com.bmo.common.market_service.model.phone.PhoneUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;

    private final PhoneMapper phoneMapper;

    @Override
    public Phone getPhoneByIdAndUserId(UUID userId, UUID phoneId) {
        return phoneRepository.findByIdAndUserId(phoneId, userId)
            .orElseThrow(() -> new EntityNotFoundException("Phone", phoneId));
    }

    @Override
    public List<Phone> getAllPhonesByUserId(UUID userId) {
        return phoneRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public Phone addPhoneToUser(UUID userId, PhoneCreateDto newPhone) {
        if (newPhone.getIsPrimary() && phoneRepository.existsByUserIdAndIsPrimaryIsTrue(userId)) {
            throw new MarketServiceBusinessException("Primary phone already exists");
        }
        Phone phone = phoneMapper.mapFromCreateDto(newPhone);
        phone.setUser(User.builder().id(userId).build());
        return phoneRepository.save(phone);
    }

    @Override
    public Phone updateUsersPhone(UUID userId, UUID phoneId, PhoneUpdateDto updatedPhone) {
        if (updatedPhone.getIsPrimary() && phoneRepository.existsByUserIdAndIsPrimaryIsTrue(userId)) {
            throw new MarketServiceBusinessException("Primary phone already exists");
        }
        return phoneRepository.findByIdAndUserId(phoneId, userId)
            .map(userFromDb -> phoneMapper.merge(userFromDb, updatedPhone))
            .map(phoneRepository::save)
            .orElseThrow(() -> new EntityNotFoundException("Phone", phoneId));
    }

    @Override
    public void deleteUsersPhone(UUID userId, UUID phoneId) {
        phoneRepository.findByIdAndUserId(phoneId, userId)
            .ifPresentOrElse(phoneRepository::delete, () -> {
                throw new EntityNotFoundException("Phone", phoneId);
            });
    }
}
