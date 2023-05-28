package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Category;
import com.bmo.common.market_service.core.mapper.CategoryMapper;
import com.bmo.common.market_service.core.mapper.PageableMapper;
import com.bmo.common.market_service.core.repository.CategoryRepository;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.category.CategoriesFilterCriteria;
import com.bmo.common.market_service.model.category.CategoryCreateDto;
import com.bmo.common.market_service.model.category.CategoryUpdateDto;
import com.bmo.common.market_service.model.exception.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Override
  public Category getCategoryById(UUID categoryId) {
    return categoryRepository.findById(categoryId)
        .orElseThrow(() -> new EntityNotFoundException("Category", categoryId));
  }

  @Override
  public Category getCategoryByIdVerbose(UUID categoryId) {
    return categoryRepository.findByIdVerbose(categoryId)
        .orElseThrow(() -> new EntityNotFoundException("Category", categoryId));
  }

  @Override
  public List<Category> getAllMainCategoriesWithDependent() {
    return categoryRepository.findAllByParentCategoryIsNull();
  }

  @Override
  public Page<Category> getCategoriesFiltered(
      CategoriesFilterCriteria categoriesFilterCriteria,
      PageRequestDto pageRequestDto) {
    return categoryRepository.findAll(PageableMapper.map(pageRequestDto));
  }

  @Override
  public Category addCategory(CategoryCreateDto newCategory) {
    Category category = categoryMapper.mapFromCreateDto(newCategory);
    return categoryRepository.save(category);
  }

  @Override
  public Category updateCategory(UUID categoryId, CategoryUpdateDto categoryUpdateDto) {
    return categoryRepository.findById(categoryId)
        .map(categoryFromDb -> categoryMapper.merge(categoryFromDb, categoryUpdateDto))
        .map(categoryRepository::save)
        .orElseThrow(() -> new EntityNotFoundException("Category", categoryId));
  }

  @Override
  public void deleteCategory(UUID categoryId) {
    categoryRepository.deleteById(categoryId);
  }
}
