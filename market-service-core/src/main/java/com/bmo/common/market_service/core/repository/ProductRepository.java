package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}