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
        name = "coding_question_skills",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_coding_question_skill",
                        columnNames = {
                                "coding_question_id",
                                "skill_uuid"
                        }
                )
        },
        indexes = {
                @Index(
                        name = "idx_coding_question_skills_question_id",
                        columnList = "coding_question_id"
                ),
                @Index(
                        name = "idx_coding_question_skills_skill_uuid",
                        columnList = "skill_uuid"
                )
        }
)
public class CodingQuestionSkillEntity {

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
     * Internal ID of the coding question.
     *
     * This is a relationship within the coding module.
     */
    @Column(
            name = "coding_question_id",
            nullable = false
    )
    private Long codingQuestionId;

    /**
     * UUID of the skill from the existing
     * candidate_skills master skill catalog.
     *
     * No database foreign key is intentionally used.
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

    public CodingQuestionSkillEntity() {
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

    public Long getCodingQuestionId() {
        return codingQuestionId;
    }

    public UUID getSkillUuid() {
        return skillUuid;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCodingQuestionId(Long codingQuestionId) {
        this.codingQuestionId = codingQuestionId;
    }

    public void setSkillUuid(UUID skillUuid) {
        this.skillUuid = skillUuid;
    }
}
