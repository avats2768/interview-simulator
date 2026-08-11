package com.simulator.backend.coding.repository;

import com.simulator.backend.coding.entity.CodingQuestionSkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CodingQuestionSkillRepository
        extends JpaRepository<CodingQuestionSkillEntity, Long> {

    Optional<CodingQuestionSkillEntity> findByUuid(UUID uuid);

    List<CodingQuestionSkillEntity> findAllByCodingQuestionId(
            Long codingQuestionId
    );

    List<CodingQuestionSkillEntity> findAllByCodingQuestionIdOrderByCreatedAtAsc(
            Long codingQuestionId
    );

    List<CodingQuestionSkillEntity> findAllBySkillUuid(
            UUID skillUuid
    );

    Optional<CodingQuestionSkillEntity> findByCodingQuestionIdAndSkillUuid(
            Long codingQuestionId,
            UUID skillUuid
    );

    boolean existsByCodingQuestionIdAndSkillUuid(
            Long codingQuestionId,
            UUID skillUuid
    );

    void deleteAllByCodingQuestionId(
            Long codingQuestionId
    );
}