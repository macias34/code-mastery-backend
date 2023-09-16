package com.macias34.codemastery.course.service;

import com.macias34.codemastery.course.dto.category.CategoryDto;
import com.macias34.codemastery.course.dto.category.CategoryRequestDto;
import com.macias34.codemastery.course.entity.CategoryEntity;
import com.macias34.codemastery.course.mapper.CategoryMapper;
import com.macias34.codemastery.course.repository.CategoryRepository;
import com.macias34.codemastery.exception.ResourceNotFoundException;
import com.macias34.codemastery.util.DtoValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository,CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryDto createCategory(CategoryRequestDto dto){
        DtoValidator.validate(dto);

        CategoryEntity category = new CategoryEntity(dto.getName());

        categoryRepository.save(category);

        return categoryMapper.fromEntityToDto(category);
    }

    @Transactional
    public CategoryDto updateDto(int id, CategoryRequestDto dto){
        DtoValidator.validate(dto);

        CategoryEntity category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found"));
        category.setName(dto.getName());

        categoryRepository.save(category);

        return categoryMapper.fromEntityToDto(category);
    }

    public CategoryDto deleteCategoryById(int id){
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found"));

        categoryRepository.deleteById(category.getId());

        return categoryMapper.fromEntityToDto(category);
    }

    public List<CategoryDto> getAllCategories(){
        List<CategoryEntity> categories = categoryRepository.findAll();

        if(categories.isEmpty()){
            throw new ResourceNotFoundException("Categories not found");
        }

        return categories.stream().map(categoryMapper::fromEntityToDto).toList();
    }

    public CategoryDto getCategoryById(int id){
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found"));

        return categoryMapper.fromEntityToDto(category);
    }
}
