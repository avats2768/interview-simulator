package com.simulator.backend.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "candidate_profiles",
        indexes = {
                @Index(
                        name = "idx_candidate_profile_uuid",
                        columnList = "uuid"
                ),
                @Index(
                        name = "idx_candidate_profile_user_uuid",
                        columnList = "user_uuid"
                )
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

    @Column(
            nullable = false,
            unique = true,
            length = 36
    )
    private String uuid;

    @Column(
            name = "user_uuid",
            nullable = false,
            unique = true,
            length = 36
    )
    private String userUuid;

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    @Column(length = 20)
    private String phone;

    /**
     * Cloudinary secure URL.
     */
    @Column(columnDefinition = "TEXT")
    private String profileImage;

    /**
     * Cloudinary public ID.
     */
    @Column(length = 500)
    private String profileImagePublicId;

    @Column(length = 150)
    private String headline;

    @Column
    private Integer yearsOfExperience;

    @Column(length = 150)
    private String currentCompany;

    @Column(length = 150)
    private String currentPosition;

    @Column(length = 150)
    private String preferredRole;

    @Column(
            precision = 10,
            scale = 2
    )
    private BigDecimal currentCTC;

    @Column(
            precision = 10,
            scale = 2
    )
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

    /**
     * Selected skill IDs.
     *
     * PostgreSQL column:
     * bigint[]
     *
     * Example:
     * {1,2,5,8}
     */
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(
            name = "skill_ids",
            columnDefinition = "bigint[]"
    )
    private Long[] skillIds;

    @Builder.Default
    @Column(
            name = "profile_completed",
            nullable = false
    )
    private Boolean profileCompleted = false;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {

        if (uuid == null || uuid.isBlank()) {
            uuid = UUID.randomUUID().toString();
        }

        LocalDateTime now = LocalDateTime.now();

        if (createdAt == null) {
            createdAt = now;
        }

        if (updatedAt == null) {
            updatedAt = now;
        }

        if (profileCompleted == null) {
            profileCompleted = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}