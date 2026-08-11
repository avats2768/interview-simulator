package com.simulator.backend.coding.repository;

import com.simulator.backend.coding.entity.CodingTestQuestionEntity;
import com.simulator.backend.coding.enums.CodingQuestionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CodingTestQuestionRepository
        extends JpaRepository<CodingTestQuestionEntity, Long> {

    Optional<CodingTestQuestionEntity> findByUuid(UUID uuid);

    List<CodingTestQuestionEntity> findAllByCodingTestId(
            Long codingTestId
    );

    List<CodingTestQuestionEntity>
    findAllByCodingTestIdOrderByQuestionOrderAsc(
            Long codingTestId
    );

    Optional<CodingTestQuestionEntity>
    findByCodingTestIdAndQuestionOrder(
            Long codingTestId,
            Integer questionOrder
    );

    List<CodingTestQuestionEntity> findAllByCodingQuestionId(
            Long codingQuestionId
    );

    Optional<CodingTestQuestionEntity>
    findByCodingTestIdAndCodingQuestionId(
            Long codingTestId,
            Long codingQuestionId
    );

    boolean existsByCodingTestIdAndQuestionOrder(
            Long codingTestId,
            Integer questionOrder
    );

    boolean existsByCodingTestIdAndCodingQuestionId(
            Long codingTestId,
            Long codingQuestionId
    );

    long countByCodingTestId(
            Long codingTestId
    );

    long countByCodingTestIdAndStatus(
            Long codingTestId,
            CodingQuestionStatus status
    );

    void deleteAllByCodingTestId(
            Long codingTestId
    );
}