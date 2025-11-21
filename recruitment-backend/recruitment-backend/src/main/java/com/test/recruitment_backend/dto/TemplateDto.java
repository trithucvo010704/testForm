package com.test.recruitment_backend.dto;

import lombok.Data;

@Data
public class TemplateDto {
    private TemplateDetail passTemplate;
    private TemplateDetail failTemplate;
}
