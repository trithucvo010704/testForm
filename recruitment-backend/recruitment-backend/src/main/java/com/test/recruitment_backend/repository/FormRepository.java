package com.test.recruitment_backend.repository;

import com.test.recruitment_backend.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRepository extends JpaRepository<Form, Long> {
    Form findByBusinessIdAndType(Long businessId, Form.Type type);
}