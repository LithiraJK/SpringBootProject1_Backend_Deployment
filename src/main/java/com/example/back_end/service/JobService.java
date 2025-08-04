package com.example.back_end.service;

import com.example.back_end.dto.JobDTO;
import com.example.back_end.entity.Job;

import java.util.List;

public interface JobService {
    void saveJob(JobDTO jobDTO);

    void updateJob(Job job);

    List<Job> getAllJob();

    void changeStatus(String jobId);

    List<JobDTO> getAllJobByKeyword(String keyword);
}