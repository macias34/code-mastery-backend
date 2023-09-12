package com.macias34.codemastery.course.mapper;

import com.macias34.codemastery.course.entity.ChapterEntity;
import com.macias34.codemastery.course.dto.chapter.ChapterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = LessonMapper.class)
public interface ChapterMapper {
    @Mapping(target = "courseId", source = "course.id")
    ChapterDto fromEntityToDto(ChapterEntity chapterEntity);
}
