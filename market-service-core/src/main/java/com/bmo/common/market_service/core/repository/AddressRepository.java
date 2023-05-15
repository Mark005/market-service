package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}