package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, UUID> {
}