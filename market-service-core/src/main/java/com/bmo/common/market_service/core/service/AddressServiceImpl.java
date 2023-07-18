package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Address;
import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.core.mapper.AddressMapper;
import com.bmo.common.market_service.core.repository.AddressRepository;
import com.bmo.common.market_service.model.address.AddressCreateDto;
import com.bmo.common.market_service.model.address.AddressUpdateDto;
import com.bmo.common.market_service.model.exception.EntityNotFoundException;
import com.bmo.common.market_service.model.exception.MarketServiceBusinessException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

  private final AddressRepository addressRepository;

  private final AddressMapper addressMapper;

  @Override
  public Address getAddressByIdAndUserId(UUID userId, UUID addressId) {
    return addressRepository.findByIdAndUserId(addressId, userId)
        .orElseThrow(() -> new EntityNotFoundException("Address", addressId));
  }

  @Override
  public List<Address> getAllAddressesByUserId(UUID userId) {
    return addressRepository.findAllByUserId(userId);
  }

  @Override
  public Address addAddressToUser(UUID userId, AddressCreateDto newAddress) {
    if (newAddress.getIsPrimary() && addressRepository.existsByUserIdAndIsPrimaryIsTrue(userId)) {
      throw new MarketServiceBusinessException("Primary address already exists");
    }
    Address address = addressMapper.mapFromCreateDto(newAddress);
    address.setUser(User.builder().id(userId).build());
    return addressRepository.save(address);
  }

  @Override
  @Transactional
  public Address updateUsersAddress(UUID userId, UUID addressId, AddressUpdateDto updatedAddress) {
    Address address = addressRepository.findByIdAndUserId(addressId, userId)
        .orElseThrow(() -> new EntityNotFoundException("Address", addressId));

    if (addressRepository.existsByUserIdAndIsPrimaryIsTrue(userId)) {
      throw new MarketServiceBusinessException("Primary address already exists");
    }

    Address merged = addressMapper.merge(address, updatedAddress);

    return addressRepository.save(merged);
  }

  @Override
  public void deleteUsersAddress(UUID userId, UUID addressId) {
    addressRepository.findByIdAndUserId(addressId, userId)
        .ifPresentOrElse(addressRepository::delete,
            () -> {
              throw new EntityNotFoundException(
                  "Address with id [%s] for user with id [%s] not found".formatted(addressId, userId));
            });
  }
}
