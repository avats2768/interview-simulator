package com.simulator.backend.coding.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "coding_test_skills",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_coding_test_skill",
                        columnNames = {
                                "coding_test_id",
                                "skill_uuid"
                        }
                )
        },
        indexes = {
                @Index(
                        name = "idx_coding_test_skills_test_id",
                        columnList = "coding_test_id"
                ),
                @Index(
                        name = "idx_coding_test_skills_skill_uuid",
                        columnList = "skill_uuid"
                )
        }
)
public class CodingTestSkillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "uuid",
            nullable = false,
            unique = true,
            updatable = false
    )
    private UUID uuid;

    /**
     * Internal ID of the coding test.
     *
     * This is a real database relationship to coding_tests.id.
     */
    @Column(
            name = "coding_test_id",
            nullable = false
    )
    private Long codingTestId;

    /**
     * UUID of the skill from the existing candidate_skills
     * master skill catalog.
     *
     * This is intentionally NOT a database foreign key.
     */
    @Column(
            name = "skill_uuid",
            nullable = false
    )
    private UUID skillUuid;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private Instant createdAt;

    public CodingTestSkillEntity() {
    }

    @PrePersist
    protected void onCreate() {

        if (uuid == null) {
            uuid = UUID.randomUUID();
        }

        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Long getCodingTestId() {
        return codingTestId;
    }

    public UUID getSkillUuid() {
        return skillUuid;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCodingTestId(Long codingTestId) {
        this.codingTestId = codingTestId;
    }

    public void setSkillUuid(UUID skillUuid) {
        this.skillUuid = skillUuid;
    }
}
