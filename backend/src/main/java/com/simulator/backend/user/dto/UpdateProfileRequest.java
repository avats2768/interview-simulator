package com.simulator.backend.user.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProfileRequest {

    @Size(max = 100, message = "First name cannot exceed 100 characters.")
    private String firstName;

    @Size(max = 100, message = "Last name cannot exceed 100 characters.")
    private String lastName;

    @Pattern(
            regexp = "^[0-9+\\-()\\s]{7,20}$",
            message = "Invalid phone number."
    )
    private String phone;

    @Size(max = 150)
    private String headline;

    @Min(value = 0, message = "Experience cannot be negative.")
    @Max(value = 60, message = "Invalid years of experience.")
    private Integer yearsOfExperience;

    @Size(max = 150)
    private String currentCompany;

    @Size(max = 150)
    private String currentPosition;

    @Size(max = 150)
    private String preferredRole;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal currentCTC;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal expectedCTC;

    @Size(max = 100)
    private String noticePeriod;

    @Size(max = 100)
    private String city;

    @Size(max = 100)
    private String country;

    @Pattern(
            regexp = "^(https?://)?(www\\.)?linkedin\\.com/.*$",
            message = "Invalid LinkedIn URL."
    )
    private String linkedinUrl;

    @Pattern(
            regexp = "^(https?://)?(www\\.)?github\\.com/.*$",
            message = "Invalid GitHub URL."
    )
    private String githubUrl;

    @Pattern(
            regexp = "^(https?://).+$",
            message = "Invalid Portfolio URL."
    )
    private String portfolioUrl;

    @Size(max = 1000)
    private String bio;

}
