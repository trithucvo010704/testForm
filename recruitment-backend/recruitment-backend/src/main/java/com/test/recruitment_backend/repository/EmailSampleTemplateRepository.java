package com.test.recruitment_backend.repository;

import com.test.recruitment_backend.entity.EmailSampleTemplate;
import com.test.recruitment_backend.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailSampleTemplateRepository extends JpaRepository<EmailSampleTemplate, Long> {
    List<EmailSampleTemplate> findByType(Form.Type type);
}