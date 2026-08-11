package com.simulator.backend.coding.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "coding_test_cases",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_coding_test_case_order",
                        columnNames = {
                                "coding_question_id",
                                "test_case_order"
                        }
                )
        },
        indexes = {
                @Index(
                        name = "idx_coding_test_cases_question_id",
                        columnList = "coding_question_id"
                )
        }
)
public class CodingTestCaseEntity {

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
     * Internal ID of the coding question.
     *
     * This belongs to the coding module and therefore
     * maps to coding_questions.id.
     */
    @Column(
            name = "coding_question_id",
            nullable = false
    )
    private Long codingQuestionId;

    /**
     * Position of this test case for the question.
     *
     * Example:
     * 1, 2, 3, 4...
     */
    @Column(
            name = "test_case_order",
            nullable = false
    )
    private Integer testCaseOrder;

    /**
     * Raw input passed to the submitted program.
     */
    @Column(
            name = "input_data",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String inputData;

    /**
     * Expected output against which the actual
     * program output will eventually be evaluated.
     */
    @Column(
            name = "expected_output",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String expectedOutput;

    /**
     * Indicates whether this test case is shown
     * to the candidate as an example.
     */
    @Column(
            name = "is_sample",
            nullable = false
    )
    private Boolean sample;

    /**
     * Indicates whether this test case should be hidden
     * from the candidate during evaluation.
     */
    @Column(
            name = "is_hidden",
            nullable = false
    )
    private Boolean hidden;

    /**
     * Weight of this test case when calculating
     * the question score.
     */
    @Column(
            name = "weight",
            nullable = false,
            precision = 5,
            scale = 2
    )
    private BigDecimal weight;

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

    public CodingTestCaseEntity() {
    }

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

        if (sample == null) {
            sample = false;
        }

        if (hidden == null) {
            hidden = true;
        }

        if (weight == null) {
            weight = BigDecimal.ONE;
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

    public Long getCodingQuestionId() {
        return codingQuestionId;
    }

    public Integer getTestCaseOrder() {
        return testCaseOrder;
    }

    public String getInputData() {
        return inputData;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public Boolean getSample() {
        return sample;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setCodingQuestionId(Long codingQuestionId) {
        this.codingQuestionId = codingQuestionId;
    }

    public void setTestCaseOrder(Integer testCaseOrder) {
        this.testCaseOrder = testCaseOrder;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public void setSample(Boolean sample) {
        this.sample = sample;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}
