package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.CartProduct;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct, UUID> {

  Optional<CartProduct> findByCartUserIdAndProductId(UUID userId, UUID productId);

  void deleteAllByCartUserId(UUID userId);
}