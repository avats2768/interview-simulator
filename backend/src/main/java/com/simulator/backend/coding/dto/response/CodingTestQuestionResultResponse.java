package com.simulator.backend.coding.dto.response;

import com.simulator.backend.coding.enums.CodingQuestionStatus;

import java.math.BigDecimal;
import java.util.UUID;

public class CodingTestQuestionResultResponse {

    private UUID uuid;

    private UUID questionUuid;

    private Integer questionOrder;

    private BigDecimal maxScore;

    private BigDecimal score;

    private CodingQuestionStatus status;

    private Integer submissionCount;

    private CodingSubmissionResponse latestSubmission;

    public CodingTestQuestionResultResponse() {
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

    public BigDecimal getMaxScore() {
        return maxScore;
    }

    public BigDecimal getScore() {
        return score;
    }

    public CodingQuestionStatus getStatus() {
        return status;
    }

    public Integer getSubmissionCount() {
        return submissionCount;
    }

    public CodingSubmissionResponse getLatestSubmission() {
        return latestSubmission;
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

    public void setMaxScore(BigDecimal maxScore) {
        this.maxScore = maxScore;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public void setStatus(CodingQuestionStatus status) {
        this.status = status;
    }

    public void setSubmissionCount(Integer submissionCount) {
        this.submissionCount = submissionCount;
    }

    public void setLatestSubmission(
            CodingSubmissionResponse latestSubmission
    ) {
        this.latestSubmission = latestSubmission;
    }
}