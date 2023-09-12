package com.macias34.codemastery.course.mapper;

import com.macias34.codemastery.course.entity.CategoryEntity;
import com.macias34.codemastery.course.dto.category.CategoryDto;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    CategoryDto fromEntityToDto(CategoryEntity categoryEntity);
}
