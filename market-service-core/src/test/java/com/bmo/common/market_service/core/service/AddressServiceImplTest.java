package com.bmo.common.market_service.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bmo.common.market_service.core.dbmodel.Address;
import com.bmo.common.market_service.core.mapper.AddressMapper;
import com.bmo.common.market_service.core.mapper.AddressMapperImpl;
import com.bmo.common.market_service.core.repository.AddressRepository;
import com.bmo.common.market_service.model.address.AddressCreateDto;
import com.bmo.common.market_service.model.address.AddressUpdateDto;
import com.bmo.common.market_service.model.exception.EntityNotFoundException;
import com.bmo.common.market_service.model.exception.MarketServiceBusinessException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

  @Mock
  private AddressRepository addressRepository;

  @Spy
  private AddressMapper addressMapper = new AddressMapperImpl();

  @InjectMocks
  private AddressServiceImpl addressService;


  @Test
  public void testGetAddressByIdAndUserId_ExistingAddress_ReturnsAddress() {
    UUID userId = UUID.randomUUID();
    UUID addressId = UUID.randomUUID();
    Address address = new Address();

    when(addressRepository.findByIdAndUserId(addressId, userId)).thenReturn(Optional.of(address));

    Address result = addressService.getAddressByIdAndUserId(userId, addressId);

    assertEquals(address, result);
  }

  @Test
  public void testGetAddressByIdAndUserId_NonExistingAddress_ThrowsEntityNotFoundException() {
    UUID userId = UUID.randomUUID();
    UUID addressId = UUID.randomUUID();

    when(addressRepository.findByIdAndUserId(addressId, userId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class,
        () -> addressService.getAddressByIdAndUserId(userId, addressId));
  }

  @Test
  public void testGetAllAddressesByUserId_ExistingAddresses_ReturnsAddressList() {
    UUID userId = UUID.randomUUID();
    List<Address> addresses = new ArrayList<>();
    addresses.add(new Address());
    when(addressRepository.findAllByUserId(userId)).thenReturn(addresses);

    List<Address> result = addressService.getAllAddressesByUserId(userId);

    assertEquals(addresses, result);
  }

  @Test
  public void testAddAddressToUser_NewAddress_SuccessfullyAdded() {
    UUID userId = UUID.randomUUID();
    AddressCreateDto newAddressDto = AddressCreateDto.builder()
        .country("country")
        .city("city")
        .street("street")
        .building("building")
        .apartment("apartment")
        .postalCode("postalCode")
        .comment("comment")
        .isPrimary(true)
        .build();

    when(addressRepository.existsByUserIdAndIsPrimaryIsTrue(userId)).thenReturn(false);
    when(addressRepository.save(any())).thenReturn(new Address());

    Address result = addressService.addAddressToUser(userId, newAddressDto);

    verify(addressRepository).existsByUserIdAndIsPrimaryIsTrue(userId);
    verify(addressMapper).mapFromCreateDto(newAddressDto);
    verify(addressRepository).save(any());
    assertNotNull(result);
  }

  @Test
  public void testAddPrimaryAddressToUser_PrimaryAddressExists_ThrowsMarketServiceBusinessException() {
    UUID userId = UUID.randomUUID();
    AddressCreateDto newAddressDto =
        AddressCreateDto.builder()
            .isPrimary(true)
            .build();

    when(addressRepository.existsByUserIdAndIsPrimaryIsTrue(userId)).thenReturn(true);

    assertThrows(MarketServiceBusinessException.class,
        () -> addressService.addAddressToUser(userId, newAddressDto));

    verify(addressRepository).existsByUserIdAndIsPrimaryIsTrue(userId);
    verify(addressMapper, never()).mapFromCreateDto(newAddressDto);
    verify(addressRepository, never()).save(any());
  }


  @Test
  public void testUpdateUsersAddress_AddressExists_SuccessfullyUpdated() {
    UUID userId = UUID.randomUUID();
    UUID addressId = UUID.randomUUID();
    AddressUpdateDto updatedAddressDto = AddressUpdateDto.builder()
        .isPrimary(true)
        .build();
    Address existingAddress = new Address();
    Address mergedAddress = new Address();

    when(addressRepository.existsByUserIdAndIsPrimaryIsTrue(userId)).thenReturn(false);
    when(addressRepository.findByIdAndUserId(addressId, userId)).thenReturn(Optional.of(existingAddress));
    when(addressMapper.merge(existingAddress, updatedAddressDto)).thenReturn(mergedAddress);
    when(addressRepository.save(mergedAddress)).thenReturn(mergedAddress);

    Address result = addressService.updateUsersAddress(userId, addressId, updatedAddressDto);

    assertNotNull(result);
    verify(addressRepository).existsByUserIdAndIsPrimaryIsTrue(userId);
    verify(addressRepository).findByIdAndUserId(addressId, userId);
    verify(addressMapper).merge(existingAddress, updatedAddressDto);
    verify(addressRepository).save(mergedAddress);
  }

  @Test
  public void testUpdateUsersAddress_AddressDoesNotExist_ThrowsEntityNotFoundException() {
    UUID userId = UUID.randomUUID();
    UUID addressId = UUID.randomUUID();
    AddressUpdateDto updatedAddressDto = new AddressUpdateDto();

    when(addressRepository.findByIdAndUserId(addressId, userId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class,
        () -> addressService.updateUsersAddress(userId, addressId, updatedAddressDto));

    verify(addressRepository).findByIdAndUserId(addressId, userId);
    verify(addressRepository, never()).existsByUserIdAndIsPrimaryIsTrue(userId);
    verify(addressMapper, never()).merge(any(), any());
    verify(addressRepository, never()).save(any());
  }

  @Test
  public void testUpdateUsersAddressToPrimary_PrimaryAddressExists_ThrowsMarketServiceBusinessException() {
    UUID userId = UUID.randomUUID();
    UUID addressId = UUID.randomUUID();
    AddressUpdateDto updatedAddressDto = AddressUpdateDto.builder()
        .isPrimary(true)
        .build();
    Address existingAddress = new Address();

    when(addressRepository.findByIdAndUserId(addressId, userId)).thenReturn(Optional.of(existingAddress));
    when(addressRepository.existsByUserIdAndIsPrimaryIsTrue(userId)).thenReturn(true);

    assertThrows(MarketServiceBusinessException.class,
        () -> addressService.updateUsersAddress(userId, addressId, updatedAddressDto));

    verify(addressRepository).findByIdAndUserId(addressId, userId);
    verify(addressRepository).existsByUserIdAndIsPrimaryIsTrue(userId);
    verify(addressMapper, never()).merge(any(), any());
    verify(addressRepository, never()).save(any());
  }

  @Test
  public void testDeleteUsersAddress_AddressExists_SuccessfullyDeleted() {
    UUID userId = UUID.randomUUID();
    UUID addressId = UUID.randomUUID();
    Address existingAddress = new Address();

    when(addressRepository.findByIdAndUserId(addressId, userId)).thenReturn(Optional.of(existingAddress));

    addressService.deleteUsersAddress(userId, addressId);

    verify(addressRepository).findByIdAndUserId(addressId, userId);
    verify(addressRepository).delete(existingAddress);
  }

  @Test
  public void testDeleteUsersAddress_AddressDoesNotExist_ThrowsEntityNotFoundException() {
    UUID userId = UUID.randomUUID();
    UUID addressId = UUID.randomUUID();

    when(addressRepository.findByIdAndUserId(addressId, userId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class,
        () -> addressService.deleteUsersAddress(userId, addressId));

    verify(addressRepository).findByIdAndUserId(addressId, userId);
    verify(addressRepository, never()).delete(any());
  }
}