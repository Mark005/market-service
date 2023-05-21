package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Address;
import com.bmo.common.market_service.model.address.AddressCreateDto;
import com.bmo.common.market_service.model.address.AddressUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    @Override
    public List<Address> getAllAddressesByUserId(UUID userId) {
        //ToDo
        return null;
    }

    @Override
    public Address addAddressToUser(UUID userId, AddressCreateDto newAddress) {
        //ToDo
        return null;
    }

    @Override
    public Address updateUsersAddress(UUID userId, UUID addressId, AddressUpdateDto updatedAddress) {
        //ToDo
        return null;
    }

    @Override
    public void deleteUsersAddress(UUID userId, UUID addressId) {
        //ToDo
    }
}
