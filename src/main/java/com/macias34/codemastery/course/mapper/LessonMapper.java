package com.macias34.codemastery.course.mapper;

import com.macias34.codemastery.course.entity.LessonEntity;
import com.macias34.codemastery.course.dto.lesson.LessonDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LessonMapper {
    @Mapping(target = "chapterId", source = "chapter.id")
    LessonDto fromEntityToDto(LessonEntity lessonEntity);
}
