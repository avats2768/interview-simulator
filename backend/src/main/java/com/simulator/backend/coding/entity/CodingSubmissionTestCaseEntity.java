package com.simulator.backend.coding.entity;

import com.simulator.backend.coding.enums.CodingTestCaseStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "coding_submission_test_cases",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_submission_test_case",
                        columnNames = {
                                "submission_id",
                                "test_case_id"
                        }
                )
        },
        indexes = {
                @Index(
                        name = "idx_submission_test_cases_submission",
                        columnList = "submission_id"
                ),
                @Index(
                        name = "idx_submission_test_cases_test_case",
                        columnList = "test_case_id"
                ),
                @Index(
                        name = "idx_submission_test_cases_status",
                        columnList = "status"
                )
        }
)
public class CodingSubmissionTestCaseEntity {

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
            name = "submission_id",
            nullable = false
    )
    private Long submissionId;

    @Column(
            name = "test_case_id",
            nullable = false
    )
    private Long testCaseId;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 40
    )
    private CodingTestCaseStatus status;

    @Column(
            name = "actual_output",
            columnDefinition = "TEXT"
    )
    private String actualOutput;

    @Column(
            name = "expected_output",
            columnDefinition = "TEXT"
    )
    private String expectedOutput;

    @Column(name = "execution_time_ms")
    private Integer executionTimeMs;

    @Column(name = "memory_used_kb")
    private Long memoryUsedKb;

    @Column(
            name = "error_message",
            columnDefinition = "TEXT"
    )
    private String errorMessage;

    @Column(name = "executed_at")
    private Instant executedAt;


    @PrePersist
    protected void onCreate() {

        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public Long getTestCaseId() {
        return testCaseId;
    }

    public CodingTestCaseStatus getStatus() {
        return status;
    }

    public String getActualOutput() {
        return actualOutput;
    }

    public String getExpectedOutput() {
        return expectedOutput;
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

    public Instant getExecutedAt() {
        return executedAt;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public void setTestCaseId(Long testCaseId) {
        this.testCaseId = testCaseId;
    }

    public void setStatus(CodingTestCaseStatus status) {
        this.status = status;
    }

    public void setActualOutput(String actualOutput) {
        this.actualOutput = actualOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
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

    public void setExecutedAt(Instant executedAt) {
        this.executedAt = executedAt;
    }
}
