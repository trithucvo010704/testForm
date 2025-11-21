package com.test.recruitment_backend.repository;

import com.test.recruitment_backend.entity.JobRound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRoundRepository extends JpaRepository<JobRound, Long> {
    List<JobRound> findByJobPostId(Long jobId);
}