package com.macias34.codemastery.course.dto.course;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UpdateCourseDto {
    private String name;
    @Min(0)
    private double price;
    private String instructorName;
    private String description;

    private Set<Integer> categoriesIds;
}
