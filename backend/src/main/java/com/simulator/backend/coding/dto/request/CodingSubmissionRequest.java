package com.simulator.backend.coding.dto.request;

import com.simulator.backend.coding.enums.CodingProgrammingLanguage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class CodingSubmissionRequest {

    @NotNull(message = "Coding test question UUID is required.")
    private UUID codingTestQuestionUuid;

    @NotNull(message = "Programming language is required.")
    private CodingProgrammingLanguage language;

    @NotBlank(message = "Code is required.")
    @Size(
            max = 100000,
            message = "Code cannot exceed 100000 characters."
    )
    private String code;

    public CodingSubmissionRequest() {
    }

    public UUID getCodingTestQuestionUuid() {
        return codingTestQuestionUuid;
    }

    public CodingProgrammingLanguage getLanguage() {
        return language;
    }

    public String getCode() {
        return code;
    }

    public void setCodingTestQuestionUuid(
            UUID codingTestQuestionUuid
    ) {
        this.codingTestQuestionUuid = codingTestQuestionUuid;
    }

    public void setLanguage(
            CodingProgrammingLanguage language
    ) {
        this.language = language;
    }

    public void setCode(String code) {
        this.code = code;
    }
}