package com.simulator.backend.coding.repository;

import com.simulator.backend.coding.entity.CodingTestEntity;
import com.simulator.backend.coding.enums.CodingTestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CodingTestRepository
        extends JpaRepository<CodingTestEntity, Long> {

    Optional<CodingTestEntity> findByUuid(UUID uuid);

    Optional<CodingTestEntity> findByUuidAndUserUuid(
            UUID uuid,
            UUID userUuid
    );

    List<CodingTestEntity> findAllByUserUuidOrderByCreatedAtDesc(
            UUID userUuid
    );

    List<CodingTestEntity> findAllByUserUuidAndStatusOrderByCreatedAtDesc(
            UUID userUuid,
            CodingTestStatus status
    );

    boolean existsByUuid(UUID uuid);

    boolean existsByUuidAndUserUuid(
            UUID uuid,
            UUID userUuid
    );
}