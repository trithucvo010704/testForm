package com.test.recruitment_backend.service;
import com.test.recruitment_backend.entity.*;
import com.test.recruitment_backend.dto.*;
import com.test.recruitment_backend.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class JobService {
    @Autowired private JobPostRepository jobPostRepo;
    @Autowired private JobRoundRepository roundRepo;
    @Autowired private FormRepository formRepo;
    @Autowired private EmailTemplateRepository templateRepo;
    @Autowired private BusinessRepository businessRepo;
    @Autowired private EmailSampleTemplateRepository sampleRepo;

    @Transactional
    public JobPost createJob(JobCreateDto dto) {
        Business business = businessRepo.findById(dto.getBusinessId())
                .orElseThrow(() -> new RuntimeException("Business not found"));

        JobPost job = new JobPost();
        job.setBusiness(business);
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setLocation(dto.getLocation());
        job.setSalaryFrom(dto.getSalaryFrom());
        job.setSalaryTo(dto.getSalaryTo());
        job.setYoe(dto.getYoe());
        job.setUnit(dto.getYoeUnit());
        if (dto.getStatus() != null) {
            try {
                job.setStatus(JobPost.Status.valueOf(dto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                job.setStatus(JobPost.Status.ACTIVE);
            }
        }
        if (dto.getDeadline() != null) {
            job.setPublishedAt(dto.getDeadline());
        }
        job.setRoundCount(dto.getRoundCount());
        job = jobPostRepo.save(job);

        // Tạo rounds
        for (int i = 0; i < dto.getRoundCount(); i++) {
            JobRound round = new JobRound();
            round.setJobPost(job);
            round.setRoundIndex(i + 1);
            round.setRoundName(dto.getRounds().get(i).getRoundName());
            round.setIsConfirmed(dto.getRounds().get(i).getIsConfirmed());
            roundRepo.save(round);
        }

        // Generate applyUrl
        job.setApplyUrl("https://yourapp.com/apply/" + job.getId() + "-" + UUID.randomUUID().toString().substring(0, 8));
        job = jobPostRepo.save(job);

        // Tạo hoặc lấy forms
        Form passForm = formRepo.findByBusinessIdAndType(business.getId(), Form.Type.PASS);
        if (passForm == null) {
            passForm = new Form();
            passForm.setBusiness(business);
            passForm.setType(Form.Type.PASS);
            passForm.setFormName("Pass Form");
            formRepo.save(passForm);
        }
        Form failForm = formRepo.findByBusinessIdAndType(business.getId(), Form.Type.FAIL);
        if (failForm == null) {
            failForm = new Form();
            failForm.setBusiness(business);
            failForm.setType(Form.Type.FAIL);
            failForm.setFormName("Fail Form");
            formRepo.save(failForm);
        }

        // Tạo templates từ dto
        List<JobRound> rounds = roundRepo.findByJobPostId(job.getId());
        for (int i = 0; i < dto.getRoundCount(); i++) {
            JobRound round = rounds.get(i);

            // Pass
            EmailTemplate passTemp = new EmailTemplate();
            passTemp.setForm(passForm);
            passTemp.setRound(round);
            passTemp.setSubject(dto.getTemplates().get(i).getPassTemplate().getSubject());
            passTemp.setContent(dto.getTemplates().get(i).getPassTemplate().getContent());
            templateRepo.save(passTemp);

            // Fail
            EmailTemplate failTemp = new EmailTemplate();
            failTemp.setForm(failForm);
            failTemp.setRound(round);
            failTemp.setSubject(dto.getTemplates().get(i).getFailTemplate().getSubject());
            failTemp.setContent(dto.getTemplates().get(i).getFailTemplate().getContent());
            templateRepo.save(failTemp);
        }

        return job;
    }

    public List<EmailSampleTemplate> getSamples(Form.Type type) {
        return sampleRepo.findByType(type);
    }
}
