package com.enelosoft.eblog.eblogapi.service.implementation;

import com.enelosoft.eblog.eblogapi.dto.CategoryDto;
import com.enelosoft.eblog.eblogapi.exception.ResourceNotFoundException;
import com.enelosoft.eblog.eblogapi.model.Category;
import com.enelosoft.eblog.eblogapi.repository.CategoryRepository;
import com.enelosoft.eblog.eblogapi.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category newCategory = categoryRepository.save(category);
        return modelMapper.map(newCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Optional<Category> category = Optional.ofNullable(categoryRepository.findById(categoryId).orElseThrow(() ->
            new ResourceNotFoundException("Category", "Id", categoryId)
        ));

        return modelMapper.map(category.get(), CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map((category -> modelMapper.map(category, CategoryDto.class)))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "Id", categoryId)
        );

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return modelMapper.map(categoryRepository.save(category), CategoryDto.class);
    }

    @Override
    public String deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Category", "Id", id)
        );

        categoryRepository.delete(category);

        return "Category successfully deleted";
    }
}
