package com.simulator.backend.user;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "candidate_profiles",
        indexes = {
                @Index(name = "idx_candidate_profile_uuid", columnList = "uuid"),
                @Index(name = "idx_candidate_profile_user_uuid", columnList = "user_uuid")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private String uuid;

    @Column(name = "user_uuid", nullable = false, unique = true, length = 36)
    private String userUuid;

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String profileImage;

    @Column(length = 150)
    private String headline;

    private Integer yearsOfExperience;

    @Column(length = 150)
    private String currentCompany;

    @Column(length = 150)
    private String currentPosition;

    @Column(length = 150)
    private String preferredRole;

    @Column(precision = 10, scale = 2)
    private BigDecimal currentCTC;

    @Column(precision = 10, scale = 2)
    private BigDecimal expectedCTC;

    @Column(length = 100)
    private String noticePeriod;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String country;

    @Column(columnDefinition = "TEXT")
    private String linkedinUrl;

    @Column(columnDefinition = "TEXT")
    private String githubUrl;

    @Column(columnDefinition = "TEXT")
    private String portfolioUrl;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Builder.Default
    private Boolean profileCompleted = false;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        if (this.uuid == null || this.uuid.isBlank()) {
            this.uuid = UUID.randomUUID().toString();
        }

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (profileCompleted == null) {
            profileCompleted = false;
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
