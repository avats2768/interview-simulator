package com.simulator.backend.coding.dto.request;

import com.simulator.backend.coding.enums.CodingDifficulty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public class CodingTestSetupRequest {

    @NotEmpty(message = "At least one coding skill must be selected.")
    @Size(
            min = 1,
            max = 10,
            message = "You can select between 1 and 10 coding skills."
    )
    private List<UUID> skillUuids;

    @NotNull(message = "Coding difficulty is required.")
    private CodingDifficulty difficulty;

    @NotNull(message = "Question count is required.")
    @Min(
            value = 1,
            message = "Question count must be at least 1."
    )
    @Max(
            value = 20,
            message = "Question count cannot exceed 20."
    )
    private Integer questionCount;

    @NotNull(message = "Duration is required.")
    @Min(
            value = 5,
            message = "Duration must be at least 5 minutes."
    )
    @Max(
            value = 180,
            message = "Duration cannot exceed 180 minutes."
    )
    private Integer durationMinutes;

    public CodingTestSetupRequest() {
    }

    public List<UUID> getSkillUuids() {
        return skillUuids;
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

    public void setSkillUuids(List<UUID> skillUuids) {
        this.skillUuids = skillUuids;
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
}
