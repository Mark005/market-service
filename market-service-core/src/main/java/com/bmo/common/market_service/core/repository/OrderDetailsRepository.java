package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, UUID> {
}