package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.Cart;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, UUID> {

  @EntityGraph(value = "fullCart")
  Optional<Cart> getCartByUserId(UUID userId);
}