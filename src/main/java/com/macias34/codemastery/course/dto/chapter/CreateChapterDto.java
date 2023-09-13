package com.macias34.codemastery.course.dto.chapter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateChapterDto {
    private String name;
    private int courseId;
}
