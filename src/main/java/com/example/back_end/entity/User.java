package com.example.back_end.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder // Constructor for Any Parameter Count
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Database will generate the ID
    private Long id;
    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
