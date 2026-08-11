package com.simulator.backend.user.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProfileRequest {

    @Size(
            max = 100,
            message = "First name cannot exceed 100 characters."
    )
    private String firstName;

    @Size(
            max = 100,
            message = "Last name cannot exceed 100 characters."
    )
    private String lastName;

    @Pattern(
            regexp = "^[0-9+\\-()\\s]{7,20}$",
            message = "Invalid phone number."
    )
    private String phone;

    @Size(
            max = 150,
            message = "Headline cannot exceed 150 characters."
    )
    private String headline;

    @Min(
            value = 0,
            message = "Experience cannot be negative."
    )
    @Max(
            value = 60,
            message = "Invalid years of experience."
    )
    private Integer yearsOfExperience;

    @Size(
            max = 150,
            message = "Current company cannot exceed 150 characters."
    )
    private String currentCompany;

    @Size(
            max = 150,
            message = "Current position cannot exceed 150 characters."
    )
    private String currentPosition;

    @Size(
            max = 150,
            message = "Preferred role cannot exceed 150 characters."
    )
    private String preferredRole;

    @DecimalMin(
            value = "0.0",
            inclusive = true,
            message = "Current CTC cannot be negative."
    )
    private BigDecimal currentCTC;

    @DecimalMin(
            value = "0.0",
            inclusive = true,
            message = "Expected CTC cannot be negative."
    )
    private BigDecimal expectedCTC;

    @Size(
            max = 100,
            message = "Notice period cannot exceed 100 characters."
    )
    private String noticePeriod;

    @Size(
            max = 100,
            message = "City cannot exceed 100 characters."
    )
    private String city;

    @Size(
            max = 100,
            message = "Country cannot exceed 100 characters."
    )
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

    @Size(
            max = 1000,
            message = "Bio cannot exceed 1000 characters."
    )
    private String bio;

    /**
     * Selected skill IDs.
     *
     * Example:
     *
     * [1, 3, 5, 8]
     *
     * Maximum 20 skills can be selected.
     */
    @Size(
            max = 20,
            message = "You can select a maximum of 20 skills."
    )
    private Long[] skillIds;
}