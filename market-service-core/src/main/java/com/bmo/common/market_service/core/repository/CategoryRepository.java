package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}