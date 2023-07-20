package com.bmo.common.market_service.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bmo.common.market_service.core.dbmodel.Category;
import com.bmo.common.market_service.core.mapper.CategoryMapper;
import com.bmo.common.market_service.core.repository.CategoryRepository;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.category.CategoriesFilterCriteria;
import com.bmo.common.market_service.model.category.CategoryCreateDto;
import com.bmo.common.market_service.model.category.CategoryUpdateDto;
import com.bmo.common.market_service.model.exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

  @Mock
  private CategoryRepository categoryRepository;

  @Mock
  private CategoryMapper categoryMapper;

  @InjectMocks
  private CategoryServiceImpl categoryService;

  @Test
  public void testGetCategoryById_CategoryExists_ReturnsCategory() {
    UUID categoryId = UUID.randomUUID();
    Category category = new Category();

    when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

    Category result = categoryService.getCategoryById(categoryId);

    assertEquals(category, result);
    verify(categoryRepository).findById(categoryId);
  }

  @Test
  public void testGetCategoryById_CategoryDoesNotExist_ThrowsEntityNotFoundException() {
    UUID categoryId = UUID.randomUUID();

    when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class,
        () -> categoryService.getCategoryById(categoryId));

    verify(categoryRepository).findById(categoryId);
  }

  @Test
  public void testGetCategoryByIdVerbose_CategoryExists_ReturnsCategory() {
    UUID categoryId = UUID.randomUUID();
    Category category = new Category();

    when(categoryRepository.findByIdVerbose(categoryId)).thenReturn(Optional.of(category));

    Category result = categoryService.getCategoryByIdVerbose(categoryId);

    assertNotNull(result);
    assertEquals(category, result);
    verify(categoryRepository).findByIdVerbose(categoryId);
  }

  @Test
  public void testGetCategoryByIdVerbose_CategoryDoesNotExist_ThrowsEntityNotFoundException() {
    UUID categoryId = UUID.randomUUID();

    when(categoryRepository.findByIdVerbose(categoryId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class,
        () -> categoryService.getCategoryByIdVerbose(categoryId));

    verify(categoryRepository).findByIdVerbose(categoryId);
  }

  @Test
  public void testGetAllMainCategoriesWithDependent_MainCategoriesExist_ReturnsList() {
    Category mainCategory1 = new Category();
    Category mainCategory2 = new Category();
    Category dependentCategory1 = new Category();
    Category dependentCategory2 = new Category();

    mainCategory1.setSubCategories(Set.of(dependentCategory1, dependentCategory2));

    List<Category> mainCategories = List.of(mainCategory1, mainCategory2);

    when(categoryRepository.findAllByParentCategoryIsNull()).thenReturn(mainCategories);

    List<Category> result = categoryService.getAllMainCategoriesWithDependent();

    assertEquals(2, result.size());
    assertTrue(result.contains(mainCategory1));
    assertTrue(result.contains(mainCategory2));
    verify(categoryRepository).findAllByParentCategoryIsNull();
  }

  @Test
  public void testGetAllMainCategoriesWithDependent_MainCategoriesDoNotExist_ReturnsEmptyList() {
    when(categoryRepository.findAllByParentCategoryIsNull()).thenReturn(List.of());

    List<Category> result = categoryService.getAllMainCategoriesWithDependent();

    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(categoryRepository).findAllByParentCategoryIsNull();
  }

  @Test
  public void testGetCategoriesFiltered_ReturnsPageOfCategories() {
    CategoriesFilterCriteria filterCriteria = new CategoriesFilterCriteria();
    PageRequestDto pageRequestDto = new PageRequestDto(10, 0);

    List<Category> categories = new ArrayList<>();
    categories.add(new Category());
    categories.add(new Category());

    Page<Category> categoryPage = new PageImpl<>(categories);

    when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoryPage);

    Page<Category> result = categoryService.getCategoriesFiltered(filterCriteria, pageRequestDto);

    assertEquals(categoryPage, result);
    verify(categoryRepository).findAll(any(Pageable.class));
  }

  @Test
  public void testAddCategory_CategorySuccessfullyAdded_ReturnsCategory() {
    CategoryCreateDto newCategoryDto = new CategoryCreateDto();
    Category newCategory = new Category();

    when(categoryMapper.mapFromCreateDto(newCategoryDto)).thenReturn(newCategory);
    when(categoryRepository.save(newCategory)).thenReturn(newCategory);

    Category result = categoryService.addCategory(newCategoryDto);

    assertEquals(newCategory, result);
    verify(categoryMapper).mapFromCreateDto(newCategoryDto);
    verify(categoryRepository).save(newCategory);
  }

  @Test
  public void testUpdateCategory_CategoryExists_SuccessfullyUpdated() {
    UUID categoryId = UUID.randomUUID();
    CategoryUpdateDto categoryUpdateDto = new CategoryUpdateDto();
    Category categoryFromDb = new Category();
    Category updatedCategory = new Category();

    when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryFromDb));
    when(categoryMapper.merge(categoryFromDb, categoryUpdateDto)).thenReturn(updatedCategory);
    when(categoryRepository.save(updatedCategory)).thenReturn(updatedCategory);

    Category result = categoryService.updateCategory(categoryId, categoryUpdateDto);

    assertNotNull(result);
    assertEquals(updatedCategory, result);
    verify(categoryRepository).findById(categoryId);
    verify(categoryMapper).merge(categoryFromDb, categoryUpdateDto);
    verify(categoryRepository).save(updatedCategory);
  }

  @Test
  public void testUpdateCategory_CategoryDoesNotExist_ThrowsEntityNotFoundException() {
    UUID categoryId = UUID.randomUUID();
    CategoryUpdateDto categoryUpdateDto = new CategoryUpdateDto();

    when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class,
        () -> categoryService.updateCategory(categoryId, categoryUpdateDto));
    verify(categoryRepository).findById(categoryId);
    verify(categoryMapper, never()).merge(any(), any());
    verify(categoryRepository, never()).save(any());
  }

  @Test
  public void testDeleteCategory_CategoryExists_SuccessfullyDeleted() {
    UUID categoryId = UUID.randomUUID();

    categoryService.deleteCategory(categoryId);

    verify(categoryRepository).deleteById(categoryId);
  }

}