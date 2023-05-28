package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.Cart;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, UUID> {

}