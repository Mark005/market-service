package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.ProductItem;
import com.bmo.common.market_service.core.dbmodel.enums.ProductItemStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductItemRepository extends JpaRepository<ProductItem, UUID>, JpaSpecificationExecutor<ProductItem> {

  Integer countByProductIdAndStatus(UUID productId, ProductItemStatus status);

  List<ProductItem> findAllByProductIdAndStatus(UUID productId, ProductItemStatus status, Pageable pageable);
}