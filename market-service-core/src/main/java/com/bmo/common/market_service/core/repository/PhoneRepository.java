package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhoneRepository extends JpaRepository<Phone, UUID> {
}