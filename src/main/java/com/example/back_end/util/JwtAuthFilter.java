package com.example.back_end.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { // filter chain ekata aluthin filter ekk api da gnnwa

        final String authHeader=request.getHeader("Authorization");// request ekata giyama header ekak thiyenawa nam eken Authorization . eke tyena token eka read krnawa
        final String jwtToken;
        final String username;

        System.out.println("Authorization Header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }
        jwtToken=authHeader.substring(7); // authHeader eke tyena token ara gnna mul characters 7 ain kra gnn one

        System.out.println("JWT Token: " + jwtToken);
        // Extract username from the JWT token
        username=jwtUtil.extractUsername(jwtToken);

        System.out.println("Extracted Username: " + username);

        if (username!= null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            System.out.println("UserDetails:  "+userDetails);
            System.out.println("UserDetails Authorities: " + userDetails.getAuthorities());

            // Validate the JWT token
            if (jwtUtil.validateToken(jwtToken)) {
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                System.out.println("Auth Token : "+authToken);

                // Set the details of the authentication token
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
