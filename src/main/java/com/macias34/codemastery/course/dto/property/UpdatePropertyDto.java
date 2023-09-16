package com.macias34.codemastery.course.dto.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdatePropertyDto {
    private String label;
    private String value;
}
