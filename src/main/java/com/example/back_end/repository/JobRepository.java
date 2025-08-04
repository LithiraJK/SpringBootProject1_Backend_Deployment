package com.example.back_end.repository;

import com.example.back_end.entity.Job;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional // Transaction handle
@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    @Modifying
    @Query(value = "UPDATE job SET status='Deactivated' WHERE id=  ?1 ", nativeQuery = true) // native query , HQL ,JPQL
    void updateJobStatus(String jobId); // custom query

    List<Job> findJobByJobTitleContainingIgnoreCase(String keyword);
}
