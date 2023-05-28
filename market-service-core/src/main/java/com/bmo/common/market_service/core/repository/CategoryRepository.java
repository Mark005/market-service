package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.Category;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

}