package com.macias34.codemastery.course.service;

import com.macias34.codemastery.course.dto.course.CourseDto;
import com.macias34.codemastery.course.dto.course.CourseResponseDto;
import com.macias34.codemastery.course.dto.course.CreateCourseDto;
import com.macias34.codemastery.course.entity.CategoryEntity;
import com.macias34.codemastery.course.entity.CourseEntity;
import com.macias34.codemastery.course.mapper.CourseMapper;
import com.macias34.codemastery.course.model.CourseFilter;
import com.macias34.codemastery.course.repository.CategoryRepository;
import com.macias34.codemastery.course.repository.CourseRepository;
import com.macias34.codemastery.exception.ResourceNotFoundException;
import com.macias34.codemastery.util.DateTimeUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final CourseMapper courseMapper;

    public CourseResponseDto searchCourses(CourseFilter courseFilter, int page, int size){
        Pageable paging = PageRequest.of(page, size);
        Page<CourseEntity> coursesPage = this.courseRepository.searchCourseEntitiesByFilters(courseFilter,paging);
        List<CourseEntity> courses = coursesPage.getContent();

        if(courses.isEmpty()){
            throw new ResourceNotFoundException("Courses not found");
        }

        List<CourseDto> courseDtos = courses.stream().map(courseMapper::fromEntityToDto).toList();

        return CourseResponseDto.builder().
                currentPage(page)
                .totalElements(coursesPage.getTotalElements())
                .totalPages(coursesPage.getTotalPages())
                .courses(courseDtos).build();
    }

    public CourseDto getCourseById(int id){
        CourseEntity course = courseRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Course not found"));

        return courseMapper.fromEntityToDto(course);
    }

    public CourseDto deleteCourseById(int id){
        CourseEntity course = courseRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Course not found"));

        courseRepository.deleteById(id);

        return courseMapper.fromEntityToDto(course);
    }

    public CourseDto createCourse(CreateCourseDto dto){
        CourseEntity courseEntity = new CourseEntity(
                dto.getName(),
                dto.getPrice(),
                dto.getInstructorName(),
                0,
                dto.getDescription()
        );

        Set<CategoryEntity> categories = new HashSet<>();

        for (int categoryId: dto.getCategoriesIds()){
            Optional<CategoryEntity> category = categoryRepository.findById(categoryId);
            category.ifPresent(categories::add);
//            Maybe need to add course to cousers in category entity
        }

        courseEntity.setCategories(categories);

        courseRepository.save(courseEntity);

        return courseMapper.fromEntityToDto(courseEntity);
    }
}
