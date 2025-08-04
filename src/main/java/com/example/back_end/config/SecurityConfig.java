package com.example.back_end.config;

import com.example.back_end.util.JwtAuthFilter;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
//@EnableWebSecurity // meka dmmoth role base access denn bri weno
@EnableMethodSecurity // This annotation enables method-level security, allowing you to use annotations like @PreAuthorize and @Secured on methods.
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { //http request eke dewal check kranw
        http.csrf(AbstractHttpConfigurer::disable) // Cross-Site Request Forgery (CSRF) protection is disabled
                .cors(Customizer.withDefaults()) // CORS (Cross-Origin Resource Sharing) is enabled with default settings
                .authorizeHttpRequests(
                        auth->
                                auth.requestMatchers("/auth/**").permitAll() // Allow unauthenticated access to /auth/** endpoints
                                        .anyRequest().authenticated()) // All other requests require authentication
                .sessionManagement(
                        session->
                                session.sessionCreationPolicy
                                        (SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider
                = new DaoAuthenticationProvider();
        daoAuthenticationProvider
                .setUserDetailsService(userDetailsService);
        daoAuthenticationProvider
                .setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;

    }


}
