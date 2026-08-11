package com.simulator.backend.coding.entity;

import com.simulator.backend.coding.enums.CodingQuestionStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "coding_test_questions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_coding_test_question_order",
                        columnNames = {
                                "coding_test_id",
                                "question_order"
                        }
                )
        },
        indexes = {
                @Index(
                        name = "idx_coding_test_questions_test_id",
                        columnList = "coding_test_id"
                ),
                @Index(
                        name = "idx_coding_test_questions_question_id",
                        columnList = "coding_question_id"
                ),
                @Index(
                        name = "idx_coding_test_questions_test_order",
                        columnList = "coding_test_id, question_order"
                )
        }
)
public class CodingTestQuestionEntity {

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
            name = "coding_test_id",
            nullable = false
    )
    private Long codingTestId;

    @Column(
            name = "coding_question_id",
            nullable = false
    )
    private Long codingQuestionId;

    @Column(
            name = "question_order",
            nullable = false
    )
    private Integer questionOrder;

    @Column(
            name = "max_score",
            nullable = false,
            precision = 5,
            scale = 2
    )
    private BigDecimal maxScore;

    @Column(
            name = "score",
            precision = 5,
            scale = 2
    )
    private BigDecimal score;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 30
    )
    private CodingQuestionStatus status;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

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
            status = CodingQuestionStatus.NOT_STARTED;
        }

        if (maxScore == null) {
            maxScore = new BigDecimal("100.00");
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

    public Long getCodingTestId() {
        return codingTestId;
    }

    public Long getCodingQuestionId() {
        return codingQuestionId;
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

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setCodingTestId(Long codingTestId) {
        this.codingTestId = codingTestId;
    }

    public void setCodingQuestionId(Long codingQuestionId) {
        this.codingQuestionId = codingQuestionId;
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

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }
}
