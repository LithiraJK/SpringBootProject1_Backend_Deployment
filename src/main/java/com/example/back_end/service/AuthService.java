package com.example.back_end.service;

import com.example.back_end.dto.request.RegisterDTO;
import com.example.back_end.dto.request.AuthDTO;
import com.example.back_end.dto.response.AuthResponseDTO;
import com.example.back_end.entity.Role;
import com.example.back_end.entity.User;
import com.example.back_end.repository.UserRepository;
import com.example.back_end.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponseDTO authenticate(AuthDTO authDTO) {
        User user= userRepository
                .findByUsername(authDTO.getUsername()).orElseThrow(
                                ()->new UsernameNotFoundException
                                        ("Username not found")); //user innwad blanwa db eke klin
        if (!passwordEncoder.matches(
                authDTO.getPassword(),// user input krapu password eka
                user.getPassword())) { // Db eke tyena password eka
            throw new BadCredentialsException("Incorrect password");
        }
        String token=jwtUtil.generateToken(authDTO.getUsername());
        String username = user.getUsername();
        String role = user.getRole().name();
        return  new AuthResponseDTO(token, username, role);
    }

    public String register(RegisterDTO  registerDTO) {
        if(userRepository.findByUsername(registerDTO.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }
        User user=User.builder()
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .role(Role.valueOf(registerDTO.getRole()))
                .email(registerDTO.getEmail())
                .build();
        userRepository.save(user);
        return  "User Registration Success";
    }
}
