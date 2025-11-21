package com.test.recruitment_backend.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class JobCreateDto {
    private Long businessId;
    private String title;
    private String description;
    private String location;
    private Integer salaryFrom;
    private Integer salaryTo;
    private Double yoe;
    private String yoeUnit;  // "Năm" hoặc "Tháng"
    private String status;  // "Draft", "Active", "Closed"
    private LocalDateTime deadline;
    private Integer roundCount;
    private List<RoundDto> rounds;
    private List<TemplateDto> templates;  // pass/fail per round
}



