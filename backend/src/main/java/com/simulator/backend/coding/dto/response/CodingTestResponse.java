package com.simulator.backend.coding.dto.response;

import com.simulator.backend.coding.enums.CodingDifficulty;
import com.simulator.backend.coding.enums.CodingTestStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class CodingTestResponse {

    private UUID uuid;

    private CodingDifficulty difficulty;

    private Integer questionCount;

    private Integer durationMinutes;

    private CodingTestStatus status;

    private BigDecimal score;

    private Integer totalQuestions;

    private Integer answeredQuestions;

    private Integer passedQuestions;

    private Instant startedAt;

    private Instant submittedAt;

    private Instant completedAt;

    private Instant createdAt;

    private List<CodingTestSkillResponse> skills;

    private List<CodingTestQuestionResponse> questions;

    public CodingTestResponse() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public CodingDifficulty getDifficulty() {
        return difficulty;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public CodingTestStatus getStatus() {
        return status;
    }

    public BigDecimal getScore() {
        return score;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public Integer getAnsweredQuestions() {
        return answeredQuestions;
    }

    public Integer getPassedQuestions() {
        return passedQuestions;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getSubmittedAt() {
        return submittedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public List<CodingTestSkillResponse> getSkills() {
        return skills;
    }

    public List<CodingTestQuestionResponse> getQuestions() {
        return questions;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setDifficulty(CodingDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setStatus(CodingTestStatus status) {
        this.status = status;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public void setAnsweredQuestions(Integer answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    public void setPassedQuestions(Integer passedQuestions) {
        this.passedQuestions = passedQuestions;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public void setSubmittedAt(Instant submittedAt) {
        this.submittedAt = submittedAt;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setSkills(List<CodingTestSkillResponse> skills) {
        this.skills = skills;
    }

    public void setQuestions(List<CodingTestQuestionResponse> questions) {
        this.questions = questions;
    }
}
