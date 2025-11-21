package com.test.recruitment_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "email_sample_template")
@Data

public class EmailSampleTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Form.Type type;
    private String sampleName;
    private String subject;
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();
}
