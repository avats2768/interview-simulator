package com.simulator.backend.user;

import com.simulator.backend.cloudinary.CloudinaryService;
import com.simulator.backend.cloudinary.CloudinaryUploadResponse;
import com.simulator.backend.user.dto.ProfileResponse;
import com.simulator.backend.user.dto.UpdateProfileRequest;
import com.simulator.backend.user.mapper.ProfileEntityMapper;
import com.simulator.backend.user.utils.ProfileCompletionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final ProfileEntityMapper profileEntityMapper;

    private final ProfileCompletionUtil profileCompletionUtil;

    private final CloudinaryService cloudinaryService;

    /**
     * Get logged-in user's profile.
     *
     * If profile does not exist, a new empty profile
     * is created for the user.
     */
    @Transactional
    public ProfileResponse getMyProfile(
            String userUuid
    ) {

        ProfileEntity profile =
                profileRepository
                        .findByUserUuid(userUuid)
                        .orElseGet(
                                () -> createEmptyProfile(userUuid)
                        );

        updateCompletionStatus(profile);

        return profileEntityMapper.toResponse(
                profile
        );
    }

    /**
     * Update logged-in user's profile.
     *
     * This updates normal profile information
     * and selected skill IDs.
     */
    @Transactional
    public ProfileResponse updateProfile(
            String userUuid,
            UpdateProfileRequest request
    ) {

        ProfileEntity profile =
                profileRepository
                        .findByUserUuid(userUuid)
                        .orElseGet(
                                () -> createEmptyProfile(userUuid)
                        );

        updateProfileFields(
                profile,
                request
        );

        updateSkillIds(
                profile,
                request
        );

        updateCompletionStatus(profile);

        ProfileEntity savedProfile =
                profileRepository.save(profile);

        return profileEntityMapper.toResponse(
                savedProfile
        );
    }

    /**
     * Upload or replace the logged-in user's
     * profile image.
     *
     * New image is uploaded first.
     * Database is updated second.
     * Old image is deleted last.
     */
    @Transactional
    public ProfileResponse uploadProfileImage(
            String userUuid,
            MultipartFile image
    ) {

        ProfileEntity profile =
                profileRepository
                        .findByUserUuid(userUuid)
                        .orElseGet(
                                () -> createEmptyProfile(userUuid)
                        );

        /*
         * Keep old Cloudinary public ID.
         */
        String oldPublicId =
                profile.getProfileImagePublicId();

        /*
         * Upload new image.
         */
        CloudinaryUploadResponse uploadResponse =
                cloudinaryService.uploadProfileImage(
                        image,
                        userUuid
                );

        /*
         * Update profile with new Cloudinary data.
         */
        profile.setProfileImage(
                uploadResponse.getSecureUrl()
        );

        profile.setProfileImagePublicId(
                uploadResponse.getPublicId()
        );

        /*
         * Recalculate completion.
         */
        updateCompletionStatus(profile);

        ProfileEntity savedProfile;

        try {

            savedProfile =
                    profileRepository.save(profile);

        } catch (RuntimeException exception) {

            /*
             * Database update failed.
             *
             * Remove newly uploaded Cloudinary asset
             * to prevent an orphan file.
             */
            try {

                cloudinaryService.deleteProfileImage(
                        uploadResponse.getPublicId()
                );

            } catch (RuntimeException cleanupException) {

                /*
                 * Don't hide the original database exception.
                 */
            }

            throw exception;
        }

        /*
         * Delete old Cloudinary image only after
         * the new image has been saved successfully.
         */
        if (
                oldPublicId != null
                        && !oldPublicId.isBlank()
                        && !oldPublicId.equals(
                        uploadResponse.getPublicId()
                )
        ) {

            try {

                cloudinaryService.deleteProfileImage(
                        oldPublicId
                );

            } catch (RuntimeException exception) {

                /*
                 * New image is already valid.
                 *
                 * Don't fail the profile update because
                 * old-image cleanup failed.
                 */
            }
        }

        return profileEntityMapper.toResponse(
                savedProfile
        );
    }

    /**
     * Delete the logged-in user's profile image.
     */
    @Transactional
    public ProfileResponse deleteProfileImage(
            String userUuid
    ) {

        ProfileEntity profile =
                profileRepository
                        .findByUserUuid(userUuid)
                        .orElseGet(
                                () -> createEmptyProfile(userUuid)
                        );

        /*
         * Keep the old public ID before clearing it.
         */
        String oldPublicId =
                profile.getProfileImagePublicId();

        /*
         * Remove image information from database.
         */
        profile.setProfileImage(null);

        profile.setProfileImagePublicId(null);

        /*
         * Recalculate profile completion.
         */
        updateCompletionStatus(profile);

        ProfileEntity savedProfile =
                profileRepository.save(profile);

        /*
         * Delete Cloudinary image.
         */
        if (
                oldPublicId != null
                        && !oldPublicId.isBlank()
        ) {

            try {

                cloudinaryService.deleteProfileImage(
                        oldPublicId
                );

            } catch (RuntimeException exception) {

                /*
                 * Database is already updated.
                 *
                 * Don't restore the old image information.
                 * Cloudinary cleanup can be retried later.
                 */
            }
        }

        return profileEntityMapper.toResponse(
                savedProfile
        );
    }

    /**
     * Create an empty profile for a user.
     */
    private ProfileEntity createEmptyProfile(
            String userUuid
    ) {

        ProfileEntity profile =
                ProfileEntity.builder()
                        .userUuid(userUuid)
                        .profileCompleted(false)
                        .skillIds(new Long[0])
                        .build();

        return profileRepository.save(
                profile
        );
    }

    /**
     * Update normal profile fields.
     *
     * These fields are intentionally not accepted
     * from the request:
     *
     * - id
     * - uuid
     * - userUuid
     * - profileImage
     * - profileImagePublicId
     * - createdAt
     * - updatedAt
     */
    private void updateProfileFields(
            ProfileEntity profile,
            UpdateProfileRequest request
    ) {

        profile.setFirstName(
                request.getFirstName()
        );

        profile.setLastName(
                request.getLastName()
        );

        profile.setPhone(
                request.getPhone()
        );

        profile.setHeadline(
                request.getHeadline()
        );

        profile.setYearsOfExperience(
                request.getYearsOfExperience()
        );

        profile.setCurrentCompany(
                request.getCurrentCompany()
        );

        profile.setCurrentPosition(
                request.getCurrentPosition()
        );

        profile.setPreferredRole(
                request.getPreferredRole()
        );

        profile.setCurrentCTC(
                request.getCurrentCTC()
        );

        profile.setExpectedCTC(
                request.getExpectedCTC()
        );

        profile.setNoticePeriod(
                request.getNoticePeriod()
        );

        profile.setCity(
                request.getCity()
        );

        profile.setCountry(
                request.getCountry()
        );

        profile.setLinkedinUrl(
                request.getLinkedinUrl()
        );

        profile.setGithubUrl(
                request.getGithubUrl()
        );

        profile.setPortfolioUrl(
                request.getPortfolioUrl()
        );

        profile.setBio(
                request.getBio()
        );
    }

    /**
     * Update selected skill IDs.
     *
     * Example:
     *
     * [1, 3, 5]
     *
     * is stored in PostgreSQL as:
     *
     * {1,3,5}
     */
    private void updateSkillIds(
            ProfileEntity profile,
            UpdateProfileRequest request
    ) {

        if (request.getSkillIds() == null) {

            profile.setSkillIds(
                    new Long[0]
            );

            return;
        }

        profile.setSkillIds(
                request.getSkillIds()
        );
    }

    /**
     * Calculate profile completion and update
     * profileCompleted flag.
     */
    private void updateCompletionStatus(
            ProfileEntity profile
    ) {

        int completion =
                profileCompletionUtil.calculate(
                        profile
                );

        profile.setProfileCompleted(
                completion == 100
        );
    }
}