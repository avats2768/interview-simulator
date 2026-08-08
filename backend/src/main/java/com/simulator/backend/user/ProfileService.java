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
    public ProfileResponse getMyProfile(String userUuid) {

        ProfileEntity profile =
                profileRepository
                        .findByUserUuid(userUuid)
                        .orElseGet(
                                () -> createEmptyProfile(userUuid)
                        );

        updateCompletionStatus(profile);

        return profileEntityMapper.toResponse(profile);
    }

    /**
     * Update logged-in user's profile.
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

        updateCompletionStatus(profile);

        ProfileEntity savedProfile =
                profileRepository.save(profile);

        return profileEntityMapper.toResponse(
                savedProfile
        );
    }

    /**
     * Upload or replace the logged-in user's profile image.
     *
     * Flow:
     *
     * 1. Find user's profile.
     * 2. Upload new image to Cloudinary.
     * 3. Save new URL and public ID in database.
     * 4. Delete old Cloudinary image.
     *
     * The old image is deleted only after the new image
     * has successfully been uploaded and saved.
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
         * Store old public ID before replacing it.
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
         * Update database with new Cloudinary data.
         */
        profile.setProfileImage(
                uploadResponse.getSecureUrl()
        );

        profile.setProfileImagePublicId(
                uploadResponse.getPublicId()
        );

        /*
         * Recalculate profile completion because
         * profileImage is one of the completion fields.
         */
        updateCompletionStatus(profile);

        ProfileEntity savedProfile;

        try {

            savedProfile =
                    profileRepository.save(profile);

        } catch (RuntimeException exception) {

            /*
             * Database update failed after Cloudinary upload.
             *
             * Try to remove the newly uploaded image
             * so that we don't leave an orphan asset.
             */
            try {

                cloudinaryService.deleteProfileImage(
                        uploadResponse.getPublicId()
                );

            } catch (RuntimeException cleanupException) {

                /*
                 * Do not hide the original database error.
                 */
            }

            throw exception;
        }

        /*
         * Delete old image only after the new image
         * has been successfully saved.
         *
         * If deletion fails, we don't fail the profile
         * update because the new image is already valid.
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
                 * New image is already successfully stored.
                 *
                 * The old Cloudinary image can be cleaned up
                 * later. Don't make the user's profile update
                 * fail because of this cleanup operation.
                 */
            }
        }

        return profileEntityMapper.toResponse(
                savedProfile
        );
    }

    /**
     * Delete the logged-in user's profile image.
     *
     * Flow:
     *
     * 1. Find profile.
     * 2. Store Cloudinary public ID.
     * 3. Remove image information from database.
     * 4. Delete image from Cloudinary.
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
         * Delete Cloudinary asset after the database
         * has been successfully updated.
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
                 * Don't restore the old URL because the
                 * Cloudinary deletion can be retried later.
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
                        .build();

        return profileRepository.save(
                profile
        );
    }

    /**
     * Copy allowed fields from request to entity.
     *
     * These fields are intentionally NOT updated:
     *
     * - id
     * - uuid
     * - userUuid
     * - profileImage
     * - profileImagePublicId
     * - createdAt
     * - updatedAt
     *
     * Profile image is managed separately by Cloudinary.
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