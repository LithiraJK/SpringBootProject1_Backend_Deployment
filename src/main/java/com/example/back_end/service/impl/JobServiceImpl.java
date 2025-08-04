package com.example.back_end.service.impl;

import com.example.back_end.dto.JobDTO;
import com.example.back_end.entity.Job;
import com.example.back_end.exceptions.ResourceNotFound;
import com.example.back_end.repository.JobRepository;
import com.example.back_end.service.JobService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final ModelMapper modelMapper; // model 2k map kra gnna puluwan (DTO <-> Entity)

    public void saveJob(JobDTO jobDTO) {

//        Job job = new Job();
//        job.setJobTitle(jobDTO.getJobTitle());
//        job.setCompany(jobDTO.getCompany());
//        job.setLocation(jobDTO.getLocation());
//        job.setType(jobDTO.getType());
//        job.setJobDescription(jobDTO.getJobDescription());

        jobRepository.save(modelMapper.map(jobDTO, Job.class));
        System.out.println("Job saved");

    }

    public void updateJob(Job job) {
        if (job==null||job.getId()==null){
            throw new IllegalArgumentException("Job Id cannot be null");
        }
        jobRepository.findById(job.getId()).orElseThrow(
                ()->new ResourceNotFound("Job Not Found"));
        jobRepository.save(job);

    }

    @Override
    public List<Job> getAllJob() {
        List<Job> jobs = jobRepository.findAll();
        return modelMapper.map(jobs,new TypeToken<List<Job>>(){}.getType());
    }

    @Override
    public void changeStatus(String jobId) {
        jobRepository.updateJobStatus(jobId);

    }

    @Override
    public List<JobDTO> getAllJobByKeyword(String keyword) {
        // DB එකෙන් keyword එක match වෙන jobs හොයනවා
        List<Job> jobs = jobRepository.findJobByJobTitleContainingIgnoreCase(keyword);
        // Entity list එක -> DTO list එකකට convert කරනවා (front-end එකට data safe විදිහට දෙන්න)
        return modelMapper.map(jobs,new TypeToken<List<JobDTO>>(){}.getType());
    }


}
