package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Category;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.category.CategoriesFilterCriteria;
import com.bmo.common.market_service.model.category.CategoryCreateDto;
import com.bmo.common.market_service.model.category.CategoryUpdateDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  @Override
  public Category getCategoryById(UUID categoryId) {
    //ToDo
    return null;
  }

  @Override
  public List<Category> getAllMainCategoriesWithDependent() {
    //ToDo
    return null;
  }

  @Override
  public Page<Category> getCategoriesFiltered(
      CategoriesFilterCriteria categoriesFilterCriteria,
      PageRequestDto pageRequestDto) {
    //ToDo
    return null;
  }

  @Override
  public Category addCategory(CategoryCreateDto newCategory) {
    //ToDo
    return null;
  }

  @Override
  public Category updateCategory(UUID categoryId, CategoryUpdateDto categoryUpdateDto) {
    //ToDo
    return null;
  }

  @Override
  public void deleteCategory(UUID categoryId) {
    //ToDo
  }
}
