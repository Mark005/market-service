package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Address;
import com.bmo.common.market_service.model.address.AddressCreateDto;
import com.bmo.common.market_service.model.address.AddressUpdateDto;

import java.util.List;
import java.util.UUID;

public interface AddressService {
    List<Address> getAllAddressesByUserId(UUID userId);

    Address addAddressToUser(UUID userId, AddressCreateDto newAddress);

    Address updateUsersAddress(UUID userId, UUID addressId, AddressUpdateDto updatedAddress);

    void deleteUsersAddress(UUID userId, UUID addressId);
}
