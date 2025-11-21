package com.test.recruitment_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Job_Round")
@Data
public class JobRound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private JobPost jobPost;
    private Integer roundIndex;
    private String roundName;
    private Boolean isConfirmed;
    private LocalDateTime createdAt = LocalDateTime.now();
}
