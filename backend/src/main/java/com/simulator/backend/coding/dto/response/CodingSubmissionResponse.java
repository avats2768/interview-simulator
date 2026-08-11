package com.simulator.backend.coding.dto.response;

import com.simulator.backend.coding.enums.CodingProgrammingLanguage;
import com.simulator.backend.coding.enums.CodingSubmissionStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class CodingSubmissionResponse {

    private UUID uuid;

    private UUID codingTestQuestionUuid;

    private Integer submissionNumber;

    private CodingProgrammingLanguage language;

    private CodingSubmissionStatus status;

    private BigDecimal score;

    private Integer executionTimeMs;

    private Long memoryUsedKb;

    private String errorMessage;

    private Instant submittedAt;

    private Instant evaluatedAt;

    public CodingSubmissionResponse() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getCodingTestQuestionUuid() {
        return codingTestQuestionUuid;
    }

    public Integer getSubmissionNumber() {
        return submissionNumber;
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

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setCodingTestQuestionUuid(
            UUID codingTestQuestionUuid
    ) {
        this.codingTestQuestionUuid = codingTestQuestionUuid;
    }

    public void setSubmissionNumber(Integer submissionNumber) {
        this.submissionNumber = submissionNumber;
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