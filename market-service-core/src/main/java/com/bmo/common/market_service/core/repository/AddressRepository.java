package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.Address;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

  List<Address> findAllByUserId(UUID userId);

  Optional<Address> findByIdAndUserId(UUID id, UUID userId);

  boolean existsByUserIdAndIsPrimaryIsTrue(UUID userId);
}