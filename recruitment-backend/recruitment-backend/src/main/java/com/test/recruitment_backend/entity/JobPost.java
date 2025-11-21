package com.test.recruitment_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Job_Post")
@Data
public class JobPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Business business;
    private String title;
    private String description;
    private String location;
    private Integer salaryFrom;
    private Integer salaryTo;
    private String workTime;
    private Double yoe;
    private String unit;
    private Integer roundCount;
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private LocalDateTime publishedAt;
    private String applyUrl;

    public enum Status {
        ACTIVE, INACTIVE, CLOSED
    }
}
