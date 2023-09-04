package com.enelosoft.eblog.eblogapi.service;

import com.enelosoft.eblog.eblogapi.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory (CategoryDto categoryDto);
    CategoryDto getCategoryById (Long id);
    List<CategoryDto> getAllCategories();
    CategoryDto updateCategory(Long id, CategoryDto categoryDto);
    String deleteCategory(Long id);
}
