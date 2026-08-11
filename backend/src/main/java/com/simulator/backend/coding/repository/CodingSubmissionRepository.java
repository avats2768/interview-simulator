package com.simulator.backend.coding.repository;

import com.simulator.backend.coding.entity.CodingSubmissionEntity;
import com.simulator.backend.coding.enums.CodingSubmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CodingSubmissionRepository
        extends JpaRepository<CodingSubmissionEntity, Long> {

    Optional<CodingSubmissionEntity> findByUuid(UUID uuid);

    List<CodingSubmissionEntity> findAllByCodingTestQuestionId(
            Long codingTestQuestionId
    );

    List<CodingSubmissionEntity>
    findAllByCodingTestQuestionIdOrderBySubmissionNumberDesc(
            Long codingTestQuestionId
    );

    Optional<CodingSubmissionEntity>
    findByCodingTestQuestionIdAndSubmissionNumber(
            Long codingTestQuestionId,
            Integer submissionNumber
    );

    Optional<CodingSubmissionEntity>
    findTopByCodingTestQuestionIdOrderBySubmissionNumberDesc(
            Long codingTestQuestionId
    );

    List<CodingSubmissionEntity> findAllByStatus(
            CodingSubmissionStatus status
    );

    List<CodingSubmissionEntity>
    findAllByStatusOrderBySubmittedAtAsc(
            CodingSubmissionStatus status
    );

    long countByCodingTestQuestionId(
            Long codingTestQuestionId
    );

    boolean existsByUuid(UUID uuid);

    boolean existsByCodingTestQuestionIdAndSubmissionNumber(
            Long codingTestQuestionId,
            Integer submissionNumber
    );
}