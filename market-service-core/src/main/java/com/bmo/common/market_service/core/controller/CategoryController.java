package com.bmo.common.market_service.core.controller;

import com.bmo.common.market_service.core.dbmodel.Category;
import com.bmo.common.market_service.core.mapper.category.CategoryResponseDtoMapper;
import com.bmo.common.market_service.core.service.CategoryService;
import com.bmo.common.market_service.model.PageRequestDto;
import com.bmo.common.market_service.model.category.CategoriesFilterCriteria;
import com.bmo.common.market_service.model.category.CategoryCreateDto;
import com.bmo.common.market_service.model.category.CategoryResponseDto;
import com.bmo.common.market_service.model.category.CategorySimpleResponseDto;
import com.bmo.common.market_service.model.category.CategoryUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryResponseDtoMapper categoryResponseDtoMapper;

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategorySimpleResponseDto> getCategoryById(@NotNull @PathVariable("id") UUID categoryId) {

        Category category = categoryService.getCategoryById(categoryId);
        CategorySimpleResponseDto categoryResponseDto = categoryResponseDtoMapper.map(category);
        return ResponseEntity.ok(categoryResponseDto);
    }

    @GetMapping("/categories/verbose")
    public ResponseEntity<List<CategoryResponseDto>> getAllMainCategoriesWithDependent() {

        List<Category> categories = categoryService.getAllMainCategoriesWithDependent();
        List<CategoryResponseDto> categoryResponseDtos = categoryResponseDtoMapper.map(categories);
        return ResponseEntity.ok(categoryResponseDtos);
    }

    @GetMapping("/categories")
    public ResponseEntity<Page<CategorySimpleResponseDto>> getAllCategories(
            CategoriesFilterCriteria categoriesFilterCriteria,
            PageRequestDto pageRequestDto) {

        Page<Category> categoriesPage = categoryService.getCategoriesFiltered(categoriesFilterCriteria, pageRequestDto);
        Page<CategorySimpleResponseDto> categoriesDtoPage = categoriesPage.map(categoryResponseDtoMapper::map);
        return ResponseEntity.ok(categoriesDtoPage);
    }

    @PostMapping("/categories")
    public ResponseEntity<CategorySimpleResponseDto> addCategory(@RequestBody @Valid CategoryCreateDto newCategory) {

        Category category = categoryService.addCategory(newCategory);
        CategorySimpleResponseDto categorySimpleResponseDto = categoryResponseDtoMapper.map(category);
        return ResponseEntity.ok(categorySimpleResponseDto);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategorySimpleResponseDto> updateCategory(
            @NotNull @PathVariable("id") UUID categoryId,
            @RequestBody @Valid CategoryUpdateDto categoryUpdateDto) {

        Category category = categoryService.updateCategory(categoryId, categoryUpdateDto);
        CategorySimpleResponseDto categorySimpleResponseDto = categoryResponseDtoMapper.map(category);
        return ResponseEntity.ok(categorySimpleResponseDto);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(
            @NotNull @PathVariable("id") UUID categoryId) {

        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

}
