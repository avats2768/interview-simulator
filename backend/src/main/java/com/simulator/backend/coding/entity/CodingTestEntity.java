package com.simulator.backend.coding.entity;

import com.simulator.backend.coding.enums.CodingDifficulty;
import com.simulator.backend.coding.enums.CodingTestStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "coding_tests",
        indexes = {
                @Index(
                        name = "idx_coding_tests_user_uuid",
                        columnList = "user_uuid"
                ),
                @Index(
                        name = "idx_coding_tests_status",
                        columnList = "status"
                ),
                @Index(
                        name = "idx_coding_tests_created_at",
                        columnList = "created_at"
                ),
                @Index(
                        name = "idx_coding_tests_user_created",
                        columnList = "user_uuid, created_at"
                )
        }
)
public class CodingTestEntity {

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

    /**
     * UUID of the authenticated user.
     *
     * This is intentionally NOT a database foreign key.
     */
    @Column(
            name = "user_uuid",
            nullable = false
    )
    private UUID userUuid;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "difficulty",
            nullable = false,
            length = 20
    )
    private CodingDifficulty difficulty;

    @Column(
            name = "question_count",
            nullable = false
    )
    private Integer questionCount;

    @Column(
            name = "duration_minutes",
            nullable = false
    )
    private Integer durationMinutes;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 30
    )
    private CodingTestStatus status;

    @Column(
            name = "score",
            precision = 5,
            scale = 2
    )
    private BigDecimal score;

    @Column(
            name = "total_questions",
            nullable = false
    )
    private Integer totalQuestions;

    @Column(
            name = "answered_questions",
            nullable = false
    )
    private Integer answeredQuestions;

    @Column(
            name = "passed_questions",
            nullable = false
    )
    private Integer passedQuestions;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "submitted_at")
    private Instant submittedAt;

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
            status = CodingTestStatus.CREATED;
        }

        if (totalQuestions == null) {
            totalQuestions = 0;
        }

        if (answeredQuestions == null) {
            answeredQuestions = 0;
        }

        if (passedQuestions == null) {
            passedQuestions = 0;
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

    public UUID getUserUuid() {
        return userUuid;
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

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
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
}
