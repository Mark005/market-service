package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.OrderHistory;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, UUID> {

}