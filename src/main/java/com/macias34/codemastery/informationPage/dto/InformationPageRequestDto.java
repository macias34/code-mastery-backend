package com.macias34.codemastery.informationPage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
//Update or create
public class InformationPageRequestDto {
    private String title;
    private String content;
}
