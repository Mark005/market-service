package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.ProductItem;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductItemRepository extends JpaRepository<ProductItem, UUID>, JpaSpecificationExecutor<ProductItem> {

}