package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.OrderDetails;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, UUID> {

}