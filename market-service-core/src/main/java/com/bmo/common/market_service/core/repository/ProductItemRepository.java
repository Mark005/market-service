package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductItemRepository extends JpaRepository<ProductItem, UUID> {
}