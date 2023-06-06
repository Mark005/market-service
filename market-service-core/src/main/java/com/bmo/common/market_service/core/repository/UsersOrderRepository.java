package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.UsersOrder;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersOrderRepository extends JpaRepository<UsersOrder, UUID> {

  @EntityGraph("fullUsersOrder")
  Optional<UsersOrder> findByIdAndUserId(UUID orderId, UUID userId);

  @EntityGraph("fullUsersOrder")
  Page<UsersOrder> findAllByUserId(UUID userId, Pageable pageable);
}