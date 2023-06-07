package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.PaymentDetails;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, UUID> {

  PaymentDetails findByUsersOrderIdAndUsersOrderUserId(UUID usersOrderId, UUID userId);

  PaymentDetails findByIdAndUsersOrderUserId(UUID id, UUID userId);
}