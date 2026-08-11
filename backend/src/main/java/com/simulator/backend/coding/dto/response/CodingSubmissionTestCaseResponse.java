package com.simulator.backend.coding.dto.response;

import com.simulator.backend.coding.enums.CodingTestCaseStatus;

import java.time.Instant;
import java.util.UUID;

public class CodingSubmissionTestCaseResponse {

    private UUID uuid;

    private UUID testCaseUuid;

    private Integer testCaseOrder;

    private CodingTestCaseStatus status;

    private String actualOutput;

    private String expectedOutput;

    private Integer executionTimeMs;

    private Long memoryUsedKb;

    private String errorMessage;

    private Instant executedAt;

    public CodingSubmissionTestCaseResponse() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getTestCaseUuid() {
        return testCaseUuid;
    }

    public Integer getTestCaseOrder() {
        return testCaseOrder;
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

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setTestCaseUuid(UUID testCaseUuid) {
        this.testCaseUuid = testCaseUuid;
    }

    public void setTestCaseOrder(Integer testCaseOrder) {
        this.testCaseOrder = testCaseOrder;
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