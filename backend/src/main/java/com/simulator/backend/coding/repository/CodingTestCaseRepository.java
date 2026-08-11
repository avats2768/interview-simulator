package com.simulator.backend.coding.repository;

import com.simulator.backend.coding.entity.CodingTestCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CodingTestCaseRepository
        extends JpaRepository<CodingTestCaseEntity, Long> {

    Optional<CodingTestCaseEntity> findByUuid(UUID uuid);

    List<CodingTestCaseEntity> findAllByCodingQuestionId(
            Long codingQuestionId
    );

    List<CodingTestCaseEntity> findAllByCodingQuestionIdOrderByTestCaseOrderAsc(
            Long codingQuestionId
    );

    List<CodingTestCaseEntity>
    findAllByCodingQuestionIdAndSampleTrueOrderByTestCaseOrderAsc(
            Long codingQuestionId
    );

    List<CodingTestCaseEntity>
    findAllByCodingQuestionIdAndHiddenTrueOrderByTestCaseOrderAsc(
            Long codingQuestionId
    );

    Optional<CodingTestCaseEntity> findByCodingQuestionIdAndTestCaseOrder(
            Long codingQuestionId,
            Integer testCaseOrder
    );

    boolean existsByCodingQuestionIdAndTestCaseOrder(
            Long codingQuestionId,
            Integer testCaseOrder
    );

    void deleteAllByCodingQuestionId(
            Long codingQuestionId
    );
}