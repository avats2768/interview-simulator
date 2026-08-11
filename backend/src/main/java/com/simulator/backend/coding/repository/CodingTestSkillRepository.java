package com.simulator.backend.coding.repository;

import com.simulator.backend.coding.entity.CodingTestSkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CodingTestSkillRepository
        extends JpaRepository<CodingTestSkillEntity, Long> {

    Optional<CodingTestSkillEntity> findByUuid(UUID uuid);

    List<CodingTestSkillEntity> findAllByCodingTestId(
            Long codingTestId
    );

    List<CodingTestSkillEntity> findAllByCodingTestIdOrderByCreatedAtAsc(
            Long codingTestId
    );

    List<CodingTestSkillEntity> findAllBySkillUuid(
            UUID skillUuid
    );

    Optional<CodingTestSkillEntity> findByCodingTestIdAndSkillUuid(
            Long codingTestId,
            UUID skillUuid
    );

    boolean existsByCodingTestIdAndSkillUuid(
            Long codingTestId,
            UUID skillUuid
    );

    void deleteAllByCodingTestId(
            Long codingTestId
    );
}