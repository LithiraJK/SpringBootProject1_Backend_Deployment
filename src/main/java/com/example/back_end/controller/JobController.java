package com.example.back_end.controller;

import com.example.back_end.dto.JobDTO;
import com.example.back_end.entity.Job;
import com.example.back_end.service.JobService;
import com.example.back_end.util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/job")
@RequiredArgsConstructor// class ekata adala fields walata constructer throgh DI inject wenwa (lombok )
@CrossOrigin
//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@PreAuthorize("hasRole('ADMIN')|| hasRole('CLIENT')")
@Slf4j //from lombok, it will generate a logger field for us
public class JobController {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);
//    @Autowired
    private final JobService jobService; // constructor inject krnkota field ek final krna eka aniwarya we.

//    public  JobController(JobService jobService) {
//        this.jobService = jobService; // DI
//    }

    @PostMapping("create")
    public ResponseEntity<APIResponse<String>> createJob(@RequestBody @Valid JobDTO jobDTO) {

        logger.info("Job Created Successfully"); //Business logic - infomations related ewa
        logger.debug("Job Details: {}", jobDTO); // details of debugging
        logger.error(jobDTO.toString()); // system error or exception logging
        logger.trace(jobDTO.toString()); // data tracing
        logger.warn(jobDTO.toString()); // potential issues - issrhata proble ekk wenn puluwan dewal


        log.info("Job Created Successfully"); //with lombok Sl4j
        log.debug(jobDTO.toString());

        jobService.saveJob(jobDTO); // object ek service layer ekt pass kra
        return new ResponseEntity<>(new APIResponse<>(201 , "Created Successfully !" ,null ),HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<APIResponse<String>> updateJob(@Valid @RequestBody Job job) {  // id ekth ekkama JSON object eka req eka daann FN eken
        jobService.updateJob( job);
        return new ResponseEntity<>(new APIResponse<>(201 , "Updated Successfully !" ,null ),HttpStatus.OK);
    }


    @GetMapping("getall")
    public ResponseEntity<APIResponse<List<Job>>> getAllJobs() {

        List<Job> jobs = jobService.getAllJob();
       return ResponseEntity.ok(new APIResponse<>(200, "Jobs fetched successfully", jobs));
    }

//    @GetMapping("getall")
//    public ResponseEntity<APIResponse<List<Job>>> getAllJobs(
//            @RequestHeader(value = "Authorization", required = false) String authHeader) {
//
//        System.out.println("AUTH HEADER RECIEVD: " + authHeader); // Check if token is received
//
//        List<Job> jobs = jobService.getAllJob();
//        return ResponseEntity.ok(new APIResponse<>(200, "Jobs fetched successfully", jobs));
//    }


    @PatchMapping("status/{id}")
    public ResponseEntity<APIResponse<String>> changeStatus(@PathVariable("id") String jobId) {
        jobService.changeStatus(jobId);
        //call service layer to change status
        String message = "Job status changed successfully " + jobId;
        return ResponseEntity.ok(new APIResponse<>(200, message, null));
    }

    @GetMapping("search/{keyword}")
    public ResponseEntity<APIResponse<List<JobDTO>>> searchJob(@PathVariable("keyword")  String keyword) {
        List<JobDTO> jobDTOS = jobService.getAllJobByKeyword(keyword);
        return ResponseEntity.ok(new APIResponse<>(200, "Jobs fetched successfully", jobDTOS));
    }

}
