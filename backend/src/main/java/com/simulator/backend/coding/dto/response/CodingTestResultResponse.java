package com.simulator.backend.coding.dto.response;

import com.simulator.backend.coding.enums.CodingDifficulty;
import com.simulator.backend.coding.enums.CodingTestStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class CodingTestResultResponse {

    private UUID uuid;

    private CodingDifficulty difficulty;

    private CodingTestStatus status;

    private Integer totalQuestions;

    private Integer answeredQuestions;

    private Integer passedQuestions;

    private BigDecimal score;

    private Integer totalSubmissions;

    private Instant startedAt;

    private Instant submittedAt;

    private Instant completedAt;

    private List<CodingTestQuestionResultResponse> questions;

    public CodingTestResultResponse() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public CodingDifficulty getDifficulty() {
        return difficulty;
    }

    public CodingTestStatus getStatus() {
        return status;
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

    public BigDecimal getScore() {
        return score;
    }

    public Integer getTotalSubmissions() {
        return totalSubmissions;
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

    public List<CodingTestQuestionResultResponse> getQuestions() {
        return questions;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setDifficulty(CodingDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setStatus(CodingTestStatus status) {
        this.status = status;
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

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public void setTotalSubmissions(Integer totalSubmissions) {
        this.totalSubmissions = totalSubmissions;
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

    public void setQuestions(
            List<CodingTestQuestionResultResponse> questions
    ) {
        this.questions = questions;
    }
}