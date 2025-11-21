package com.test.recruitment_backend.repository;

import com.test.recruitment_backend.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {}