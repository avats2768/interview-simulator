package com.simulator.backend.user.mapper;

import com.simulator.backend.user.ProfileEntity;
import com.simulator.backend.user.dto.ProfileResponse;
import com.simulator.backend.user.utils.ProfileCompletionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileEntityMapper {

    private final ProfileCompletionUtil profileCompletionUtil;

    public ProfileResponse toResponse(ProfileEntity profile) {

        int completion = profileCompletionUtil.calculate(profile);

        return ProfileResponse.builder()
                .uuid(profile.getUuid())
                .userUuid(profile.getUserUuid())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .phone(profile.getPhone())
                .profileImage(profile.getProfileImage())
                .headline(profile.getHeadline())
                .yearsOfExperience(profile.getYearsOfExperience())
                .currentCompany(profile.getCurrentCompany())
                .currentPosition(profile.getCurrentPosition())
                .preferredRole(profile.getPreferredRole())
                .currentCTC(profile.getCurrentCTC())
                .expectedCTC(profile.getExpectedCTC())
                .noticePeriod(profile.getNoticePeriod())
                .city(profile.getCity())
                .country(profile.getCountry())
                .linkedinUrl(profile.getLinkedinUrl())
                .githubUrl(profile.getGithubUrl())
                .portfolioUrl(profile.getPortfolioUrl())
                .bio(profile.getBio())
                .profileCompleted(completion == 100)
                .completionPercentage(completion)
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }

}
