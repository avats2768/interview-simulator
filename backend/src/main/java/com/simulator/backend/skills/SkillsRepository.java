package com.simulator.backend.skills;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillsRepository
        extends JpaRepository<SkillsEntity, Long> {

    /**
     * Find skill by public UUID.
     */
    Optional<SkillsEntity> findByUuid(String uuid);

    /**
     * Check whether a skill UUID exists.
     */
    boolean existsByUuid(String uuid);

    /**
     * Find skill by exact name.
     */
    Optional<SkillsEntity> findBySkillNameIgnoreCase(
            String skillName
    );

    /**
     * Check whether a skill with the given name exists.
     */
    boolean existsBySkillNameIgnoreCase(
            String skillName
    );

    /**
     * Get all skills ordered alphabetically.
     */
    List<SkillsEntity> findAllByOrderBySkillNameAsc();

    /**
     * Get skills by category.
     */
    List<SkillsEntity> findAllByCategoryIgnoreCaseOrderBySkillNameAsc(
            String category
    );

    /**
     * Get skills by multiple UUIDs.
     */
    List<SkillsEntity> findAllByUuidIn(
            List<String> uuids
    );
}