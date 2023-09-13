package com.macias34.codemastery.course.dto.course;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CourseResponseDto {
    long totalElements;
    int totalPages;
    int currentPage;
    List<CourseDto> courses;
}
