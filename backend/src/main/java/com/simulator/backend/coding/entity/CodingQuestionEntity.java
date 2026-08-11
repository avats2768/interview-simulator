package com.simulator.backend.coding.entity;

import com.simulator.backend.coding.enums.CodingDifficulty;
import com.simulator.backend.coding.enums.CodingQuestionBankStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "coding_questions",
        indexes = {
                @Index(
                        name = "idx_coding_questions_difficulty",
                        columnList = "difficulty"
                ),
                @Index(
                        name = "idx_coding_questions_status",
                        columnList = "status"
                ),
                @Index(
                        name = "idx_coding_questions_difficulty_status",
                        columnList = "difficulty, status"
                )
        }
)
public class CodingQuestionEntity {

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

    @Column(
            name = "title",
            nullable = false,
            length = 200
    )
    private String title;

    @Column(
            name = "slug",
            nullable = false,
            unique = true,
            length = 220
    )
    private String slug;

    @Column(
            name = "description",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "difficulty",
            nullable = false,
            length = 20
    )
    private CodingDifficulty difficulty;

    @Column(
            name = "constraints",
            columnDefinition = "TEXT"
    )
    private String constraints;

    @Column(
            name = "input_format",
            columnDefinition = "TEXT"
    )
    private String inputFormat;

    @Column(
            name = "output_format",
            columnDefinition = "TEXT"
    )
    private String outputFormat;

    @Column(
            name = "examples",
            columnDefinition = "jsonb"
    )
    private String examples;

    @Column(
            name = "explanation",
            columnDefinition = "TEXT"
    )
    private String explanation;

    @Column(
            name = "starter_code",
            columnDefinition = "jsonb"
    )
    private String starterCode;

    @Column(
            name = "time_limit_ms",
            nullable = false
    )
    private Integer timeLimitMs;

    @Column(
            name = "memory_limit_mb",
            nullable = false
    )
    private Integer memoryLimitMb;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 30
    )
    private CodingQuestionBankStatus status;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private Instant createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {

        if (uuid == null) {
            uuid = UUID.randomUUID();
        }

        Instant now = Instant.now();

        if (createdAt == null) {
            createdAt = now;
        }

        if (updatedAt == null) {
            updatedAt = now;
        }

        if (status == null) {
            status = CodingQuestionBankStatus.ACTIVE;
        }

        if (timeLimitMs == null) {
            timeLimitMs = 2000;
        }

        if (memoryLimitMb == null) {
            memoryLimitMb = 256;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public String getSlug() {
        return slug;
    }

    public String getDescription() {
        return description;
    }

    public CodingDifficulty getDifficulty() {
        return difficulty;
    }

    public String getConstraints() {
        return constraints;
    }

    public String getInputFormat() {
        return inputFormat;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public String getExamples() {
        return examples;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getStarterCode() {
        return starterCode;
    }

    public Integer getTimeLimitMs() {
        return timeLimitMs;
    }

    public Integer getMemoryLimitMb() {
        return memoryLimitMb;
    }

    public CodingQuestionBankStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDifficulty(CodingDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }

    public void setInputFormat(String inputFormat) {
        this.inputFormat = inputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public void setExamples(String examples) {
        this.examples = examples;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public void setStarterCode(String starterCode) {
        this.starterCode = starterCode;
    }

    public void setTimeLimitMs(Integer timeLimitMs) {
        this.timeLimitMs = timeLimitMs;
    }

    public void setMemoryLimitMb(Integer memoryLimitMb) {
        this.memoryLimitMb = memoryLimitMb;
    }

    public void setStatus(CodingQuestionBankStatus status) {
        this.status = status;
    }
}