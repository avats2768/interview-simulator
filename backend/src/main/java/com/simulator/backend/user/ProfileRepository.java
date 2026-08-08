package com.simulator.backend.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    /**
     * Find profile by profile UUID
     */
    Optional<ProfileEntity> findByUuid(String uuid);

    /**
     * Find profile by logged-in user's UUID
     */
    Optional<ProfileEntity> findByUserUuid(String userUuid);

    /**
     * Check if profile already exists for a user
     */
    boolean existsByUserUuid(String userUuid);

    /**
     * Delete profile by profile UUID
     */
    void deleteByUuid(String uuid);

}
