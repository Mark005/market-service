package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Category;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.category.CategoriesFilterCriteria;
import com.bmo.common.market_service.model.category.CategoryCreateDto;
import com.bmo.common.market_service.model.category.CategoryUpdateDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    Category getCategoryById(UUID categoryId);

    List<Category> getAllMainCategoriesWithDependent();

    Page<Category> getCategoriesFiltered(CategoriesFilterCriteria categoriesFilterCriteria, PageRequestDto pageRequestDto);

    Category addCategory(CategoryCreateDto newCategory);

    Category updateCategory(UUID categoryId, CategoryUpdateDto categoryUpdateDto);

    void deleteCategory(UUID categoryId);
}
