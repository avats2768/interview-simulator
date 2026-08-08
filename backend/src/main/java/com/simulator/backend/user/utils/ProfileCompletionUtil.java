package com.simulator.backend.user.utils;

import com.simulator.backend.user.ProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class ProfileCompletionUtil {

    private static final int TOTAL_FIELDS = 18;

    public int calculate(ProfileEntity profile) {

        if (profile == null) {
            return 0;
        }

        int completed = 0;

        if (hasValue(profile.getFirstName())) completed++;
        if (hasValue(profile.getLastName())) completed++;
        if (hasValue(profile.getPhone())) completed++;
        if (hasValue(profile.getProfileImage())) completed++;
        if (hasValue(profile.getHeadline())) completed++;
        if (profile.getYearsOfExperience() != null) completed++;
        if (hasValue(profile.getCurrentCompany())) completed++;
        if (hasValue(profile.getCurrentPosition())) completed++;
        if (hasValue(profile.getPreferredRole())) completed++;
        if (profile.getCurrentCTC() != null) completed++;
        if (profile.getExpectedCTC() != null) completed++;
        if (hasValue(profile.getNoticePeriod())) completed++;
        if (hasValue(profile.getCity())) completed++;
        if (hasValue(profile.getCountry())) completed++;
        if (hasValue(profile.getLinkedinUrl())) completed++;
        if (hasValue(profile.getGithubUrl())) completed++;
        if (hasValue(profile.getPortfolioUrl())) completed++;
        if (hasValue(profile.getBio())) completed++;

        return Math.round((completed * 100.0f) / TOTAL_FIELDS);
    }

    private boolean hasValue(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
