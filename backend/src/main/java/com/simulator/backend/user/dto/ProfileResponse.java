package com.simulator.backend.user.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ProfileResponse {

    private String uuid;

    private String userUuid;

    private String firstName;

    private String lastName;

    private String phone;

    private String profileImage;

    private String headline;

    private Integer yearsOfExperience;

    private String currentCompany;

    private String currentPosition;

    private String preferredRole;

    private BigDecimal currentCTC;

    private BigDecimal expectedCTC;

    private String noticePeriod;

    private String city;

    private String country;

    private String linkedinUrl;

    private String githubUrl;

    private String portfolioUrl;

    private String bio;

    private Boolean profileCompleted;

    /**
     * Calculated field.
     * Not stored in database.
     */
    private Integer completionPercentage;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
