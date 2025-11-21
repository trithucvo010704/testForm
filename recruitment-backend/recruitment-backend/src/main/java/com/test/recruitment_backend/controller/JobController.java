package com.test.recruitment_backend.controller;

import com.test.recruitment_backend.dto.JobCreateDto;
import com.test.recruitment_backend.entity.EmailSampleTemplate;
import com.test.recruitment_backend.entity.Form;
import com.test.recruitment_backend.entity.JobPost;
import com.test.recruitment_backend.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8100")
public class JobController {
    @Autowired
    private JobService jobService;

    @PostMapping("/jobs")
    public ResponseEntity<JobPost> createJob(@RequestBody JobCreateDto dto) {
        JobPost job = jobService.createJob(dto);
        return ResponseEntity.ok(job);
    }

    @GetMapping("/samples/{type}")
    public ResponseEntity<List<EmailSampleTemplate>> getSamples(@PathVariable String type) {
        Form.Type enumType = Form.Type.valueOf(type.toUpperCase());
        return ResponseEntity.ok(jobService.getSamples(enumType));
    }
}
