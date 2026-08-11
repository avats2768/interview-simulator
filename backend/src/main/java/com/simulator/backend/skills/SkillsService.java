package com.simulator.backend.skills;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SkillsService {

    private final SkillsRepository skillsRepository;

    /**
     * Get all skills.
     *
     * Skills are returned alphabetically by skill name.
     */
    public List<SkillsEntity> getAllSkills() {

        return skillsRepository
                .findAllByOrderBySkillNameAsc();
    }

    /**
     * Get skills by their UUIDs.
     *
     * The UUIDs provided by the caller are used to
     * find the corresponding skills.
     *
     * Example:
     *
     * [
     *     "skill-uuid-1",
     *     "skill-uuid-2",
     *     "skill-uuid-3"
     * ]
     */
    public List<SkillsEntity> getSkillsByIds(
            List<String> skillUuids
    ) {

        if (
                skillUuids == null
                        || skillUuids.isEmpty()
        ) {
            return Collections.emptyList();
        }

        return skillsRepository
                .findAllByUuidIn(skillUuids);
    }
}