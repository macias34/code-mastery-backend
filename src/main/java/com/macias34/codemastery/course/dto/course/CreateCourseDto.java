package com.macias34.codemastery.course.dto.course;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateCourseDto {
    private String name;
    @Min(0)
    private double price;
    private String instructorName;
    private String description;
}
