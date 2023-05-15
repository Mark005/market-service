package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
}