package com.macias34.codemastery.course.controller;

import com.macias34.codemastery.course.dto.course.CourseDto;
import com.macias34.codemastery.course.dto.course.CourseResponseDto;
import com.macias34.codemastery.course.dto.course.CreateCourseDto;
import com.macias34.codemastery.course.model.CourseFilter;
import com.macias34.codemastery.course.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/course")
@AllArgsConstructor
public class CourseController {
    private CourseService courseService;

    @GetMapping("/")
    public ResponseEntity<CourseResponseDto> searchCourses(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") Double minPrice,
            @RequestParam(defaultValue = "100000") Double maxPrice,
            @RequestParam(defaultValue = "0") Integer minParticipantsCount,
            @RequestParam(defaultValue = "0") Integer categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        CourseFilter courseFilter = new CourseFilter(name,minPrice,maxPrice,minParticipantsCount,categoryId);

        return ResponseEntity.ok(courseService.searchCourses(courseFilter,page,size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(
            @PathVariable int id
    ){
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CourseDto> deleteCourseById(
            @PathVariable int id
    ){
        return ResponseEntity.ok(courseService.deleteCourseById(id));
    }

    @PostMapping("/")
    public ResponseEntity<CourseDto> createCourse(
            @RequestBody CreateCourseDto dto
    ){
        return ResponseEntity.ok(courseService.createCourse(dto));
    }
    //TODO update course

}
