package com.macias34.codemastery.course.service;

import com.macias34.codemastery.course.dto.lesson.CreateLessonDto;
import com.macias34.codemastery.course.dto.lesson.LessonDto;
import com.macias34.codemastery.course.dto.lesson.UpdateLessonDto;
import com.macias34.codemastery.course.entity.ChapterEntity;
import com.macias34.codemastery.course.entity.LessonEntity;
import com.macias34.codemastery.course.mapper.LessonMapper;
import com.macias34.codemastery.course.repository.ChapterRepository;
import com.macias34.codemastery.course.repository.LessonRepository;
import com.macias34.codemastery.exception.BadRequestException;
import com.macias34.codemastery.exception.ResourceNotFoundException;
import com.macias34.codemastery.exception.StorageException;
import com.macias34.codemastery.storage.service.StorageService;
import com.macias34.codemastery.util.DtoValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Service
public class LessonService {
    private final LessonRepository lessonRepository;
    private final ChapterRepository chapterRepository;
    private final LessonMapper lessonMapper;

    private final StorageService storageService;

    @Transactional
    public LessonDto createLesson(CreateLessonDto dto, MultipartFile file) {
        DtoValidator.validate(dto);

        ChapterEntity chapter = chapterRepository.findById(dto.getChapterId())
                .orElseThrow(() -> new ResourceNotFoundException("Chapter not found"));
        LessonEntity lesson = new LessonEntity(dto.getName(), chapter);

        List<LessonEntity> lessons = chapter.getLessons();
        lessons.add(lesson);
        chapter.setLessons(lessons);

        try {
            lessonRepository.save(lesson);
            storageService.uploadFile(lesson.getId() + ".mp4", file);
            chapterRepository.save(chapter);
        } catch (Exception e) {
            lessonRepository.deleteById(lesson.getId());
            if (e instanceof BadRequestException) {
                System.out.println(e);
            }
            throw new StorageException("Error with saving file occured");
        }
        return lessonMapper.fromEntityToDto(lesson);
    }

    public LessonDto deleteLessonById(int id) {
        LessonEntity lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

        try {
            storageService.deleteFile(lesson.getId() + ".mp4");
            lessonRepository.deleteById(lesson.getId());
        } catch (Exception e) {
            throw new StorageException("Error with removing file occurred");
        }

        return lessonMapper.fromEntityToDto(lesson);
    }

    public LessonDto updateLessonById(int id, UpdateLessonDto dto) {
        DtoValidator.validate(dto);

        LessonEntity lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

        lesson.setName(dto.getName());
        lessonRepository.save(lesson);

        return lessonMapper.fromEntityToDto(lesson);
    }

    public LessonDto getLessonById(int id) {
        LessonEntity lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

        return lessonMapper.fromEntityToDto(lesson);
    }

    public List<LessonDto> getLessonsByChapterId(int chapterId) {
        List<LessonEntity> lessons = lessonRepository.findLessonEntitiesByChapter_Id(chapterId);
        if (lessons.isEmpty()) {
            throw new ResourceNotFoundException("Lessons not found");
        }
        return lessons.stream().map(lessonMapper::fromEntityToDto).toList();
    }

    // TODO: return lesson URL
    public Resource getLessonFileById(int id) {
        try {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error with returning file for given lesson");
        }
    }
}
