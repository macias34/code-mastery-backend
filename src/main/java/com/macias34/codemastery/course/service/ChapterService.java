package com.macias34.codemastery.course.service;

import com.macias34.codemastery.course.dto.chapter.ChapterDto;
import com.macias34.codemastery.course.dto.chapter.UpdateChapterDto;
import com.macias34.codemastery.course.dto.chapter.CreateChapterDto;
import com.macias34.codemastery.course.entity.ChapterEntity;
import com.macias34.codemastery.course.entity.CourseEntity;
import com.macias34.codemastery.course.mapper.ChapterMapper;
import com.macias34.codemastery.course.repository.ChapterRepository;
import com.macias34.codemastery.course.repository.CourseRepository;
import com.macias34.codemastery.exception.ResourceNotFoundException;
import com.macias34.codemastery.util.DtoValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChapterService {
    private final ChapterRepository chapterRepository;
    private final CourseRepository courseRepository;
    private final ChapterMapper chapterMapper;

    @Transactional
    public ChapterDto createChapter(CreateChapterDto dto){
        DtoValidator.validate(dto);

        CourseEntity course = courseRepository.findById(dto.getCourseId()).orElseThrow(()-> new ResourceNotFoundException("Course not found"));
        ChapterEntity chapter = new ChapterEntity(dto.getName(),course);

        List<ChapterEntity> courseChapters = course.getChapters();
        courseChapters.add(chapter);
        course.setChapters(courseChapters);

        courseRepository.save(course);
        chapterRepository.save(chapter);

        return chapterMapper.fromEntityToDto(chapter);
    }

    public ChapterDto getChapterById(int id){
        ChapterEntity chapter = chapterRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Chapter not found"));

        return chapterMapper.fromEntityToDto(chapter);
    }

    public List<ChapterDto> getChaptersByCourseId(int courseId){
        List<ChapterEntity> chapters = chapterRepository.findChapterEntitiesByCourse_Id(courseId);

        if(chapters.isEmpty()){
            throw new ResourceNotFoundException("Chapters not found");
        }

        return chapters.stream().map(chapterMapper::fromEntityToDto).toList();
    }

    public ChapterDto deleteChapterById(int id){
        // TODO WHEN DELETED CHAPTER ALSO DELETE ALL LESSON FILES
        ChapterEntity chapter = chapterRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Chapter not found"));

        chapterRepository.deleteById(chapter.getId());

        return chapterMapper.fromEntityToDto(chapter);
    }

    public ChapterDto updateChapter(int id, UpdateChapterDto dto){
        DtoValidator.validate(dto);

        ChapterEntity chapter = chapterRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Chapter not found"));

        chapter.setName(dto.getName());

        return chapterMapper.fromEntityToDto(chapter);
    }
}
