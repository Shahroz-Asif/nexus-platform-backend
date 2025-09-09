package com.nexus.platform.mapper;

import com.nexus.platform.dto.EntrepreneurDTO;
import com.nexus.platform.entity.Startup;
import com.nexus.platform.entity.User;

public class EntrepreneurMapper {

    public static EntrepreneurDTO toDTO(User user, Startup startup) {
        return EntrepreneurDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .avatarUrl(user.getAvatarUrl())
                .bio(user.getBio())
                .isOnline(user.isOnline())
                .createdAt(user.getCreatedAt())
                .startupName(startup.getStartupName())
                .pitchSummary(startup.getPitchSummary())
                .fundingNeeded(startup.getFundingNeeded())
                .industry(startup.getIndustry())
                .location(startup.getLocation())
                .foundedYear(startup.getFoundedYear())
                .teamSize(startup.getTeamSize())
                .website(startup.getWebsite())
                .build();
    }
}
