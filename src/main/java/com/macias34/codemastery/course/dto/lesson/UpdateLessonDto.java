package com.macias34.codemastery.course.dto.lesson;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UpdateLessonDto {
    @NotNull
    private String name;
}
