package com.simulator.backend.coding.dto.response;

import com.simulator.backend.coding.enums.CodingProgrammingLanguage;
import com.simulator.backend.coding.enums.CodingSubmissionStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class CodingSubmissionResultResponse {

    private UUID uuid;

    private UUID codingTestQuestionUuid;

    private Integer submissionNumber;

    private CodingProgrammingLanguage language;

    private CodingSubmissionStatus status;

    private BigDecimal score;

    private Integer passedTestCases;

    private Integer totalTestCases;

    private Integer executionTimeMs;

    private Long memoryUsedKb;

    private String errorMessage;

    private Instant submittedAt;

    private Instant evaluatedAt;

    private List<CodingSubmissionTestCaseResponse> testCases;

    public CodingSubmissionResultResponse() {
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

    public Integer getPassedTestCases() {
        return passedTestCases;
    }

    public Integer getTotalTestCases() {
        return totalTestCases;
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

    public List<CodingSubmissionTestCaseResponse> getTestCases() {
        return testCases;
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

    public void setPassedTestCases(Integer passedTestCases) {
        this.passedTestCases = passedTestCases;
    }

    public void setTotalTestCases(Integer totalTestCases) {
        this.totalTestCases = totalTestCases;
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

    public void setTestCases(
            List<CodingSubmissionTestCaseResponse> testCases
    ) {
        this.testCases = testCases;
    }
}