package com.simulator.backend.coding.repository;

import com.simulator.backend.coding.entity.CodingQuestionEntity;
import com.simulator.backend.coding.enums.CodingDifficulty;
import com.simulator.backend.coding.enums.CodingQuestionBankStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CodingQuestionRepository
        extends JpaRepository<CodingQuestionEntity, Long> {

    Optional<CodingQuestionEntity> findByUuid(UUID uuid);

    Optional<CodingQuestionEntity> findBySlug(String slug);

    List<CodingQuestionEntity> findAllByDifficultyAndStatus(
            CodingDifficulty difficulty,
            CodingQuestionBankStatus status
    );

    List<CodingQuestionEntity> findAllByStatusOrderByCreatedAtDesc(
            CodingQuestionBankStatus status
    );

    List<CodingQuestionEntity> findAllByDifficultyAndStatusOrderByCreatedAtDesc(
            CodingDifficulty difficulty,
            CodingQuestionBankStatus status
    );

    boolean existsByUuid(UUID uuid);

    boolean existsBySlug(String slug);
}