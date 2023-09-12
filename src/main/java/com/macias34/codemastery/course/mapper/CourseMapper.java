package com.macias34.codemastery.course.mapper;

import com.macias34.codemastery.course.dto.course.CourseDto;
import com.macias34.codemastery.course.entity.CourseEntity;
import org.mapstruct.Mapper;

@Mapper(uses = {CategoryMapper.class, ChapterMapper.class})
public interface CourseMapper {
    CourseDto fromEntityToDto(CourseEntity courseEntity);
}
