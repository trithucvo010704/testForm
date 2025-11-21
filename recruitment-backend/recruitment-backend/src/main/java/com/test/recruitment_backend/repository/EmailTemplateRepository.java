package com.test.recruitment_backend.repository;

import com.test.recruitment_backend.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {}

