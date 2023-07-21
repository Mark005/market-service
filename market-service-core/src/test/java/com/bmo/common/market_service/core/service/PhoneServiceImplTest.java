package com.bmo.common.market_service.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bmo.common.market_service.core.dbmodel.Phone;
import com.bmo.common.market_service.core.mapper.PhoneMapper;
import com.bmo.common.market_service.core.repository.PhoneRepository;
import com.bmo.common.market_service.model.exception.EntityNotFoundException;
import com.bmo.common.market_service.model.exception.MarketServiceBusinessException;
import com.bmo.common.market_service.model.phone.PhoneCreateDto;
import com.bmo.common.market_service.model.phone.PhoneUpdateDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PhoneServiceImplTest {

  @Mock
  private PhoneRepository phoneRepository;

  @Mock
  private PhoneMapper phoneMapper;

  @InjectMocks
  private PhoneServiceImpl phoneService;

  @Test
  public void testGetPhoneByIdAndUserId_PhoneExists_ReturnsPhone() {
    UUID userId = UUID.randomUUID();
    UUID phoneId = UUID.randomUUID();

    Phone phone = new Phone();

    when(phoneRepository.findByIdAndUserId(phoneId, userId))
        .thenReturn(Optional.of(phone));

    Phone result = phoneService.getPhoneByIdAndUserId(userId, phoneId);

    assertEquals(phone, result);

    verify(phoneRepository).findByIdAndUserId(phoneId, userId);
  }

  @Test
  public void testGetPhoneByIdAndUserId_PhoneDoesNotExist_ThrowsEntityNotFoundException() {
    UUID userId = UUID.randomUUID();
    UUID phoneId = UUID.randomUUID();

    when(phoneRepository.findByIdAndUserId(phoneId, userId))
        .thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class,
        () -> phoneService.getPhoneByIdAndUserId(userId, phoneId));

    verify(phoneRepository).findByIdAndUserId(phoneId, userId);
  }

  @Test
  public void testGetAllPhonesByUserId_UserHasPhones_ReturnsListOfPhones() {
    UUID userId = UUID.randomUUID();
    List<Phone> phones = new ArrayList<>();
    phones.add(new Phone());
    phones.add(new Phone());
    phones.add(new Phone());

    when(phoneRepository.findAllByUserId(userId)).thenReturn(phones);

    List<Phone> result = phoneService.getAllPhonesByUserId(userId);

    assertNotNull(result);
    assertEquals(phones.size(), result.size());
    assertEquals(phones, result);

    verify(phoneRepository).findAllByUserId(userId);
  }

  @Test
  public void testGetAllPhonesByUserId_UserHasNoPhones_ReturnsEmptyList() {
    UUID userId = UUID.randomUUID();
    List<Phone> emptyList = new ArrayList<>();

    when(phoneRepository.findAllByUserId(userId)).thenReturn(emptyList);

    List<Phone> result = phoneService.getAllPhonesByUserId(userId);

    assertNotNull(result);
    assertTrue(result.isEmpty());

    verify(phoneRepository).findAllByUserId(userId);
  }

  @Test
  public void testAddPhoneToUser_NewPhoneAddedSuccessfully() {
    UUID userId = UUID.randomUUID();
    PhoneCreateDto newPhone = new PhoneCreateDto();
    newPhone.setIsPrimary(true);

    Phone mappedPhone = new Phone();

    when(phoneMapper.mapFromCreateDto(newPhone)).thenReturn(mappedPhone);
    when(phoneRepository.existsByUserIdAndIsPrimaryIsTrue(userId)).thenReturn(false);
    when(phoneRepository.save(mappedPhone)).thenReturn(mappedPhone);

    Phone result = phoneService.addPhoneToUser(userId, newPhone);

    assertEquals(mappedPhone, result);

    verify(phoneRepository).existsByUserIdAndIsPrimaryIsTrue(userId);
    verify(phoneRepository).save(mappedPhone);
  }

  @Test
  public void testAddPhoneToUser_PrimaryPhoneExists_ThrowsMarketServiceBusinessException() {
    UUID userId = UUID.randomUUID();
    PhoneCreateDto newPhone = new PhoneCreateDto();
    newPhone.setIsPrimary(true);

    when(phoneRepository.existsByUserIdAndIsPrimaryIsTrue(userId)).thenReturn(true);

    assertThrows(MarketServiceBusinessException.class,
        () -> phoneService.addPhoneToUser(userId, newPhone));

    verify(phoneRepository).existsByUserIdAndIsPrimaryIsTrue(userId);
    verify(phoneRepository, never()).save(any());
  }

  @Test
  public void testUpdateUsersPhone_PhoneUpdatedSuccessfully() {
    UUID userId = UUID.randomUUID();
    UUID phoneId = UUID.randomUUID();
    PhoneUpdateDto updatedPhone = new PhoneUpdateDto();
    updatedPhone.setIsPrimary(true);

    Phone phoneFromDb = new Phone();
    Phone mergedPhone = new Phone();

    when(phoneRepository.existsByUserIdAndIsPrimaryIsTrue(userId)).thenReturn(false);
    when(phoneRepository.findByIdAndUserId(phoneId, userId)).thenReturn(Optional.of(phoneFromDb));
    when(phoneMapper.merge(phoneFromDb, updatedPhone)).thenReturn(mergedPhone);
    when(phoneRepository.save(mergedPhone)).thenReturn(mergedPhone);

    Phone result = phoneService.updateUsersPhone(userId, phoneId, updatedPhone);

    assertEquals(mergedPhone, result);

    verify(phoneRepository).existsByUserIdAndIsPrimaryIsTrue(userId);
    verify(phoneRepository).findByIdAndUserId(phoneId, userId);
    verify(phoneRepository).save(mergedPhone);
  }

  @Test
  public void testUpdateUsersPhone_PrimaryPhoneExists_ThrowsMarketServiceBusinessException() {
    UUID userId = UUID.randomUUID();
    UUID phoneId = UUID.randomUUID();
    PhoneUpdateDto updatedPhone = new PhoneUpdateDto();
    updatedPhone.setIsPrimary(true);

    when(phoneRepository.existsByUserIdAndIsPrimaryIsTrue(userId)).thenReturn(true);

    assertThrows(MarketServiceBusinessException.class,
        () -> phoneService.updateUsersPhone(userId, phoneId, updatedPhone));
    verify(phoneRepository).existsByUserIdAndIsPrimaryIsTrue(userId);
    verify(phoneRepository, never()).findByIdAndUserId(any(), any());
    verify(phoneRepository, never()).save(any());
  }

  @Test
  public void testUpdateUsersPhone_PhoneToUpdateDoesNotExist_ThrowsEntityNotFoundException() {
    UUID userId = UUID.randomUUID();
    UUID phoneId = UUID.randomUUID();
    PhoneUpdateDto updatedPhone = new PhoneUpdateDto();
    updatedPhone.setIsPrimary(true);

    when(phoneRepository.existsByUserIdAndIsPrimaryIsTrue(userId)).thenReturn(false);
    when(phoneRepository.findByIdAndUserId(phoneId, userId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class,
        () -> phoneService.updateUsersPhone(userId, phoneId, updatedPhone));
    verify(phoneRepository).existsByUserIdAndIsPrimaryIsTrue(userId);
    verify(phoneRepository).findByIdAndUserId(phoneId, userId);
    verify(phoneRepository, never()).save(any());
  }

  @Test
  public void testDeleteUsersPhone_PhoneExists_PhoneDeletedSuccessfully() {
    UUID userId = UUID.randomUUID();
    UUID phoneId = UUID.randomUUID();

    Phone phoneFromDb = new Phone();

    when(phoneRepository.findByIdAndUserId(phoneId, userId)).thenReturn(Optional.of(phoneFromDb));

    phoneService.deleteUsersPhone(userId, phoneId);

    verify(phoneRepository).findByIdAndUserId(phoneId, userId);
    verify(phoneRepository).delete(phoneFromDb);
  }

  @Test
  public void testDeleteUsersPhone_PhoneDoesNotExist_ThrowsEntityNotFoundException() {
    UUID userId = UUID.randomUUID();
    UUID phoneId = UUID.randomUUID();

    when(phoneRepository.findByIdAndUserId(phoneId, userId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class,
        () -> phoneService.deleteUsersPhone(userId, phoneId));
    verify(phoneRepository).findByIdAndUserId(phoneId, userId);
    verify(phoneRepository, never()).delete(any());
  }
}