package com.macias34.codemastery.course.service;

import com.macias34.codemastery.course.dto.category.CategoryDto;
import com.macias34.codemastery.course.dto.category.CategoryRequestDto;
import com.macias34.codemastery.course.dto.lesson.CreateLessonDto;
import com.macias34.codemastery.course.dto.lesson.LessonDto;
import com.macias34.codemastery.course.dto.lesson.UpdateLessonDto;
import com.macias34.codemastery.course.entity.CategoryEntity;
import com.macias34.codemastery.course.entity.ChapterEntity;
import com.macias34.codemastery.course.entity.LessonEntity;
import com.macias34.codemastery.course.mapper.LessonMapper;
import com.macias34.codemastery.course.repository.ChapterRepository;
import com.macias34.codemastery.course.repository.LessonRepository;
import com.macias34.codemastery.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class LessonService {
    private final LessonRepository lessonRepository;
    private final ChapterRepository chapterRepository;
    private final LessonMapper lessonMapper;

    @Autowired
    public LessonService(LessonRepository lessonRepository,LessonMapper lessonMapper, ChapterRepository chapterRepository) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
        this.chapterRepository = chapterRepository;
    }

    @Transactional
    public LessonDto createLesson(CreateLessonDto dto){
        ChapterEntity chapter = chapterRepository.findById(dto.getChapterId()).orElseThrow(()-> new ResourceNotFoundException("Chapter not found"));
        LessonEntity lesson = new LessonEntity(dto.getName(),chapter);

        List<LessonEntity> lessons = chapter.getLessons();
        lessons.add(lesson);
        chapter.setLessons(lessons);

        lessonRepository.save(lesson);
        chapterRepository.save(chapter);

        return lessonMapper.fromEntityToDto(lesson);
    }

    public LessonDto deleteLessonById(int id){
        LessonEntity lesson = lessonRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Lesson not found"));

        lessonRepository.deleteById(lesson.getId());

        return lessonMapper.fromEntityToDto(lesson);
    }

    public LessonDto updateLessonById(int id, UpdateLessonDto dto){
        LessonEntity lesson = lessonRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Lesson not found"));

        lesson.setName(dto.getName());
        lessonRepository.save(lesson);

        return lessonMapper.fromEntityToDto(lesson);
    }

    public LessonDto getLessonById(int id){
        LessonEntity lesson = lessonRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Lesson not found"));

        return lessonMapper.fromEntityToDto(lesson);
    }
}
