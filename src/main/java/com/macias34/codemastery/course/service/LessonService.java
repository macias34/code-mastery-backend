package com.macias34.codemastery.course.service;

import com.macias34.codemastery.course.dto.lesson.CreateLessonDto;
import com.macias34.codemastery.course.dto.lesson.LessonDto;
import com.macias34.codemastery.course.dto.lesson.UpdateLessonDto;
import com.macias34.codemastery.course.entity.ChapterEntity;
import com.macias34.codemastery.course.entity.LessonEntity;
import com.macias34.codemastery.course.mapper.LessonMapper;
import com.macias34.codemastery.course.repository.ChapterRepository;
import com.macias34.codemastery.course.repository.LessonRepository;
import com.macias34.codemastery.exception.ResourceNotFoundException;
import com.macias34.codemastery.exception.StorageException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service

public class LessonService {
    private final LessonRepository lessonRepository;
    private final ChapterRepository chapterRepository;
    private final LessonMapper lessonMapper;

    private final FileStorageService storageService;

    @Autowired
    public LessonService(LessonRepository lessonRepository, LessonMapper lessonMapper, ChapterRepository chapterRepository, FileStorageService storageService) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
        this.chapterRepository = chapterRepository;
        this.storageService = storageService;
    }

    //TODO File upload
    @Transactional
    public LessonDto createLesson(CreateLessonDto dto, MultipartFile file){
        ChapterEntity chapter = chapterRepository.findById(dto.getChapterId()).orElseThrow(()-> new ResourceNotFoundException("Chapter not found"));
        LessonEntity lesson = new LessonEntity(dto.getName(),chapter);

        List<LessonEntity> lessons = chapter.getLessons();
        lessons.add(lesson);
        chapter.setLessons(lessons);

        try{
            //TODO ID IS ALWAYS 0
            storageService.save(file,lesson.getId());
            lessonRepository.save(lesson);
            chapterRepository.save(chapter);
        }catch (Exception e){
            throw e;
//            throw new StorageException("Error with saving file occured");
        }
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

    public List<LessonDto> getLessonsByChapterId(int chapterId){
        List<LessonEntity> lessons = lessonRepository.findLessonEntitiesByChapter_Id(chapterId);
        if(lessons.isEmpty()){
            throw new ResourceNotFoundException("Lessons not found");
        }
        return lessons.stream().map(lessonMapper::fromEntityToDto).toList();
    }
}
