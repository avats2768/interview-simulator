package com.simulator.backend.coding.dto.response;

import java.util.UUID;

public class CodingTestSkillResponse {

    private UUID skillUuid;

    private String skillName;

    private String category;

    private Boolean primary;

    public CodingTestSkillResponse() {
    }

    public UUID getSkillUuid() {
        return skillUuid;
    }

    public String getSkillName() {
        return skillName;
    }

    public String getCategory() {
        return category;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setSkillUuid(UUID skillUuid) {
        this.skillUuid = skillUuid;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }
}
