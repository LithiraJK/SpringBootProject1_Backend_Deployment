package com.example.back_end.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class HelloController {

    @PreAuthorize("hasRole('ADMIN')")
//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("hello")
    public String hello() {
        return "Hello, you are authenticated!";
    }
}
