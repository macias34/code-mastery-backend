package com.macias34.codemastery.course.dto.property;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdatePropertyDto {
    @Nullable
    private String label;
    @Nullable
    private String value;
}
