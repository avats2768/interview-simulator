package com.simulator.backend.user;

import com.simulator.backend.common.ApiResponse;
import com.simulator.backend.user.dto.ProfileResponse;
import com.simulator.backend.user.dto.UpdateProfileRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/candidate/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    /**
     * Get logged-in candidate profile.
     *
     * GET /candidate/profile/me
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<ProfileResponse>> getMyProfile(
            Authentication authentication
    ) {

        String userUuid = authentication.getName();

        ProfileResponse profile =
                profileService.getMyProfile(userUuid);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Profile fetched successfully.",
                        profile
                )
        );
    }

    /**
     * Update logged-in candidate profile.
     *
     * PUT /candidate/profile/me
     */
    @PutMapping("/me")
    public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateProfileRequest request
    ) {

        String userUuid = authentication.getName();

        ProfileResponse profile =
                profileService.updateProfile(
                        userUuid,
                        request
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Profile updated successfully.",
                        profile
                )
        );
    }

    /**
     * Upload or replace profile image.
     *
     * POST /candidate/profile/me/image
     */
    @PostMapping(
            value = "/me/image",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<ApiResponse<ProfileResponse>> uploadProfileImage(
            Authentication authentication,
            @RequestParam("image") MultipartFile image
    ) {

        String userUuid = authentication.getName();

        ProfileResponse profile =
                profileService.uploadProfileImage(
                        userUuid,
                        image
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Profile image uploaded successfully.",
                        profile
                )
        );
    }

    /**
     * Delete profile image.
     *
     * DELETE /candidate/profile/me/image
     */
    @DeleteMapping("/me/image")
    public ResponseEntity<ApiResponse<ProfileResponse>> deleteProfileImage(
            Authentication authentication
    ) {

        String userUuid = authentication.getName();

        ProfileResponse profile =
                profileService.deleteProfileImage(
                        userUuid
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Profile image deleted successfully.",
                        profile
                )
        );
    }
}