package com.example.back_end.repository;

import com.example.back_end.entity.Job;
import com.example.back_end.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long > {

    Optional<User> findByUsername(String username);

}
