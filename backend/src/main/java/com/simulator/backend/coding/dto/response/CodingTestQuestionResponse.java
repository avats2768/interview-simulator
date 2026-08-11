package com.simulator.backend.coding.dto.response;

import com.simulator.backend.coding.enums.CodingDifficulty;
import com.simulator.backend.coding.enums.CodingQuestionStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class CodingTestQuestionResponse {

    private UUID uuid;

    private UUID questionUuid;

    private Integer questionOrder;

    private String title;

    private String slug;

    private String description;

    private CodingDifficulty difficulty;

    private String constraints;

    private String inputFormat;

    private String outputFormat;

    private String examples;

    private String starterCode;

    private Integer timeLimitMs;

    private Integer memoryLimitMb;

    private BigDecimal maxScore;

    private BigDecimal score;

    private CodingQuestionStatus status;

    private Instant startedAt;

    private Instant completedAt;

    public CodingTestQuestionResponse() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getQuestionUuid() {
        return questionUuid;
    }

    public Integer getQuestionOrder() {
        return questionOrder;
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

    public String getStarterCode() {
        return starterCode;
    }

    public Integer getTimeLimitMs() {
        return timeLimitMs;
    }

    public Integer getMemoryLimitMb() {
        return memoryLimitMb;
    }

    public BigDecimal getMaxScore() {
        return maxScore;
    }

    public BigDecimal getScore() {
        return score;
    }

    public CodingQuestionStatus getStatus() {
        return status;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setQuestionUuid(UUID questionUuid) {
        this.questionUuid = questionUuid;
    }

    public void setQuestionOrder(Integer questionOrder) {
        this.questionOrder = questionOrder;
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

    public void setStarterCode(String starterCode) {
        this.starterCode = starterCode;
    }

    public void setTimeLimitMs(Integer timeLimitMs) {
        this.timeLimitMs = timeLimitMs;
    }

    public void setMemoryLimitMb(Integer memoryLimitMb) {
        this.memoryLimitMb = memoryLimitMb;
    }

    public void setMaxScore(BigDecimal maxScore) {
        this.maxScore = maxScore;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public void setStatus(CodingQuestionStatus status) {
        this.status = status;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }
}
