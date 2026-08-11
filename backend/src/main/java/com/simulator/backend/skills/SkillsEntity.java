package com.simulator.backend.skills;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "candidate_skills",
        indexes = {
                @Index(
                        name = "idx_candidate_skills_category",
                        columnList = "category"
                ),
                @Index(
                        name = "idx_candidate_skills_skill_name",
                        columnList = "skill_name"
                ),
                @Index(
                        name = "idx_candidate_skills_level",
                        columnList = "level"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_candidate_skills_uuid",
                        columnNames = "uuid"
                ),
                @UniqueConstraint(
                        name = "uk_candidate_skills_skill_name",
                        columnNames = "skill_name"
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillsEntity {

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
            name = "skill_name",
            nullable = false,
            length = 100,
            unique = true
    )
    private String skillName;

    @Column(length = 50)
    private String category;

    @Column(length = 30)
    private String proficiency;

    @Column(
            name = "years_of_experience",
            precision = 4,
            scale = 1
    )
    private BigDecimal yearsOfExperience;

    @Column(length = 30)
    private String level;

    @Column(
            name = "is_primary",
            nullable = false
    )
    @Builder.Default
    private Boolean isPrimary = false;

    @Column(
            name = "created_at",
            nullable = false
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

        createdAt = now;
        updatedAt = now;

        if (isPrimary == null) {
            isPrimary = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}