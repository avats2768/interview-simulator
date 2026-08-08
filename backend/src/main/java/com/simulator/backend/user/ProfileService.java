package com.simulator.backend.user;

import com.simulator.backend.user.dto.ProfileResponse;
import com.simulator.backend.user.dto.UpdateProfileRequest;
import com.simulator.backend.user.mapper.ProfileEntityMapper;
import com.simulator.backend.user.utils.ProfileCompletionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

    private final ProfileRepository ProfileEntityRepository;
    private final ProfileEntityMapper ProfileEntityMapper;
    private final ProfileCompletionUtil profileCompletionUtil;

    /**
     * Get logged-in user's profile.
     *
     * If profile does not exist, a new empty profile
     * is created for the user.
     */
    @Transactional
    public ProfileResponse getMyProfile(String userUuid) {

        ProfileEntity profile = ProfileEntityRepository
                .findByUserUuid(userUuid)
                .orElseGet(() -> createEmptyProfile(userUuid));

        updateCompletionStatus(profile);

        return ProfileEntityMapper.toResponse(profile);
    }

    /**
     * Update logged-in user's profile.
     */
    @Transactional
    public ProfileResponse updateProfile(
            String userUuid,
            UpdateProfileRequest request
    ) {

        ProfileEntity profile = ProfileEntityRepository
                .findByUserUuid(userUuid)
                .orElseGet(() -> createEmptyProfile(userUuid));

        updateProfileFields(profile, request);

        updateCompletionStatus(profile);

        ProfileEntity savedProfile =
                ProfileEntityRepository.save(profile);

        return ProfileEntityMapper.toResponse(savedProfile);
    }

    /**
     * Create an empty profile for a user.
     */
    private ProfileEntity createEmptyProfile(String userUuid) {

        ProfileEntity profile = ProfileEntity.builder()
                .userUuid(userUuid)
                .profileCompleted(false)
                .build();

        return ProfileEntityRepository.save(profile);
    }

    /**
     * Copy allowed fields from request to entity.
     *
     * Important:
     * We intentionally do NOT update:
     * - id
     * - uuid
     * - userUuid
     * - profileImage
     * - createdAt
     * - updatedAt
     *
     * profileImage will be handled separately by Cloudinary.
     */
    private void updateProfileFields(
            ProfileEntity profile,
            UpdateProfileRequest request
    ) {

        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setPhone(request.getPhone());
        profile.setHeadline(request.getHeadline());
        profile.setYearsOfExperience(request.getYearsOfExperience());
        profile.setCurrentCompany(request.getCurrentCompany());
        profile.setCurrentPosition(request.getCurrentPosition());
        profile.setPreferredRole(request.getPreferredRole());
        profile.setCurrentCTC(request.getCurrentCTC());
        profile.setExpectedCTC(request.getExpectedCTC());
        profile.setNoticePeriod(request.getNoticePeriod());
        profile.setCity(request.getCity());
        profile.setCountry(request.getCountry());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        profile.setGithubUrl(request.getGithubUrl());
        profile.setPortfolioUrl(request.getPortfolioUrl());
        profile.setBio(request.getBio());
    }

    /**
     * Calculate profile completion and update
     * profileCompleted flag.
     */
    private void updateCompletionStatus(ProfileEntity profile) {

        int completion =
                profileCompletionUtil.calculate(profile);

        profile.setProfileCompleted(completion == 100);
    }
}
