package com.example.back_end.controller;

import com.example.back_end.dto.request.RegisterDTO;
import com.example.back_end.dto.request.AuthDTO;
import com.example.back_end.dto.response.AuthResponseDTO;
import com.example.back_end.service.AuthService;
import com.example.back_end.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<APIResponse<String>> registerUser (@RequestBody RegisterDTO registerDTO) {
        return ResponseEntity.ok(new APIResponse<>(
                200,
                "User Registration Success !!",
                authService.register(registerDTO)));
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse<AuthResponseDTO>> login (@RequestBody AuthDTO authDTO) {
        return ResponseEntity.ok(new APIResponse<>(
                200,
                "User Login Success !!",
                authService.authenticate(authDTO))
        );
    }
}
