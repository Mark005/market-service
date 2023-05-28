package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.Phone;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, UUID> {

  List<Phone> findAllByUserId(UUID userId);

  Optional<Phone> findByIdAndUserId(UUID id, UUID userId);

  boolean existsByUserIdAndIsPrimaryIsTrue(UUID userId);
}