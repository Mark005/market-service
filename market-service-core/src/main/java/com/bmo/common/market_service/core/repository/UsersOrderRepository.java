package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.UsersOrder;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersOrderRepository extends JpaRepository<UsersOrder, UUID> {

  Optional<UsersOrder> findByIdAndUserId(UUID orderId, UUID userId);
}