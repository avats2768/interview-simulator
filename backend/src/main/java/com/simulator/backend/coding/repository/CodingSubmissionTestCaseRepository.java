package com.simulator.backend.coding.repository;

import com.simulator.backend.coding.entity.CodingSubmissionTestCaseEntity;
import com.simulator.backend.coding.enums.CodingTestCaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CodingSubmissionTestCaseRepository
        extends JpaRepository<CodingSubmissionTestCaseEntity, Long> {

    Optional<CodingSubmissionTestCaseEntity> findByUuid(UUID uuid);

    List<CodingSubmissionTestCaseEntity> findAllBySubmissionId(
            Long submissionId
    );

    List<CodingSubmissionTestCaseEntity>
    findAllBySubmissionIdOrderByIdAsc(
            Long submissionId
    );

    List<CodingSubmissionTestCaseEntity> findAllByTestCaseId(
            Long testCaseId
    );

    Optional<CodingSubmissionTestCaseEntity>
    findBySubmissionIdAndTestCaseId(
            Long submissionId,
            Long testCaseId
    );

    long countBySubmissionId(
            Long submissionId
    );

    long countBySubmissionIdAndStatus(
            Long submissionId,
            CodingTestCaseStatus status
    );

    boolean existsBySubmissionIdAndTestCaseId(
            Long submissionId,
            Long testCaseId
    );

    void deleteAllBySubmissionId(
            Long submissionId
    );
}