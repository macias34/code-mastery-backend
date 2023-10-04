package com.macias34.codemastery.course.dto.course;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UpdateCourseDto {
    @Nullable
    private String name;
    @Min(0)
    @Nullable
    private Double price;
    @Nullable
    private String instructorName;
    @Nullable
    private String description;
    @Nullable
    private Set<Integer> categoriesIds;
}
