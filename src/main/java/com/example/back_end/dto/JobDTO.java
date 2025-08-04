package com.example.back_end.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class JobDTO {
    @NotBlank(message = "Job title is required")
    private String jobTitle;
    @NotBlank(message = "Company name is required")
    private String company;
    @NotNull(message = "Location is required")
    private String location;
    private String type;
    private String jobDescription;
    private String status;
}
