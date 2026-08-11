package com.simulator.backend.coding.entity;

import com.simulator.backend.coding.enums.CodingProgrammingLanguage;
import com.simulator.backend.coding.enums.CodingSubmissionStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "coding_submissions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_coding_submission_number",
                        columnNames = {
                                "coding_test_question_id",
                                "submission_number"
                        }
                )
        },
        indexes = {
                @Index(
                        name = "idx_coding_submissions_test_question",
                        columnList = "coding_test_question_id"
                ),
                @Index(
                        name = "idx_coding_submissions_status",
                        columnList = "status"
                ),
                @Index(
                        name = "idx_coding_submissions_submitted_at",
                        columnList = "submitted_at"
                )
        }
)
public class CodingSubmissionEntity {

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
            name = "coding_test_question_id",
            nullable = false
    )
    private Long codingTestQuestionId;

    @Column(
            name = "submission_number",
            nullable = false
    )
    private Integer submissionNumber;

    @Column(
            name = "code",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "language",
            nullable = false,
            length = 30
    )
    private CodingProgrammingLanguage language;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 40
    )
    private CodingSubmissionStatus status;

    @Column(
            name = "score",
            precision = 5,
            scale = 2
    )
    private BigDecimal score;

    @Column(name = "execution_time_ms")
    private Integer executionTimeMs;

    @Column(name = "memory_used_kb")
    private Long memoryUsedKb;

    @Column(
            name = "error_message",
            columnDefinition = "TEXT"
    )
    private String errorMessage;

    @Column(
            name = "submitted_at",
            nullable = false
    )
    private Instant submittedAt;

    @Column(name = "evaluated_at")
    private Instant evaluatedAt;

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

        if (submittedAt == null) {
            submittedAt = now;
        }

        if (createdAt == null) {
            createdAt = now;
        }

        if (updatedAt == null) {
            updatedAt = now;
        }

        if (status == null) {
            status = CodingSubmissionStatus.QUEUED;
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

    public Long getCodingTestQuestionId() {
        return codingTestQuestionId;
    }

    public Integer getSubmissionNumber() {
        return submissionNumber;
    }

    public String getCode() {
        return code;
    }

    public CodingProgrammingLanguage getLanguage() {
        return language;
    }

    public CodingSubmissionStatus getStatus() {
        return status;
    }

    public BigDecimal getScore() {
        return score;
    }

    public Integer getExecutionTimeMs() {
        return executionTimeMs;
    }

    public Long getMemoryUsedKb() {
        return memoryUsedKb;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Instant getSubmittedAt() {
        return submittedAt;
    }

    public Instant getEvaluatedAt() {
        return evaluatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setCodingTestQuestionId(Long codingTestQuestionId) {
        this.codingTestQuestionId = codingTestQuestionId;
    }

    public void setSubmissionNumber(Integer submissionNumber) {
        this.submissionNumber = submissionNumber;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLanguage(CodingProgrammingLanguage language) {
        this.language = language;
    }

    public void setStatus(CodingSubmissionStatus status) {
        this.status = status;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public void setExecutionTimeMs(Integer executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }

    public void setMemoryUsedKb(Long memoryUsedKb) {
        this.memoryUsedKb = memoryUsedKb;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setSubmittedAt(Instant submittedAt) {
        this.submittedAt = submittedAt;
    }

    public void setEvaluatedAt(Instant evaluatedAt) {
        this.evaluatedAt = evaluatedAt;
    }
}
