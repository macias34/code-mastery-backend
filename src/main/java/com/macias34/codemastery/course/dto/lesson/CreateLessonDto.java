package com.macias34.codemastery.course.dto.lesson;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateLessonDto {
    private String name;
    private int chapterId;
}
