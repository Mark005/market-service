package com.bmo.common.market_service.core.repository;

import com.bmo.common.market_service.core.dbmodel.Category;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

  @EntityGraph(value = "categoryTree")
  @Query("from Category c where c.id = :id")
  Optional<Category> findByIdVerbose(UUID id);

  @EntityGraph(value = "categoryTree")
  List<Category> findAllByParentCategoryIsNull();
}