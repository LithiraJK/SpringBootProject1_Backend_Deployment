package com.example.back_end.config;

import com.example.back_end.entity.User;
import com.example.back_end.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class AppConfig {

//    ape nowana class bean widiyata save kra gnna

    private final UserRepository userRepository;

    @Bean
    public ModelMapper modelMapper() {  // aluthin dependency ekk danakot mehem bean ekk widiyata register kra gnna one
        return new ModelMapper();
    }

    @Bean
    public UserDetailsService userDetailsService (){
        return username -> userRepository.findByUsername(username).map(
                user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name() ))
                )
        ).orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }


}
