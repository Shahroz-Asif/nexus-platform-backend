package com.nexus.platform.mapper;

import com.nexus.platform.dto.InvestorDTO;
import com.nexus.platform.entity.InvestorProfile;
import com.nexus.platform.entity.User;

public class InvestorMapper {

    public static InvestorDTO toDTO(User user, InvestorProfile profile) {
        return InvestorDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .avatarUrl(user.getAvatarUrl())
                .bio(user.getBio())
                .isOnline(user.isOnline())
                .createdAt(user.getCreatedAt())
                .investmentInterests(profile.getInvestmentInterests())
                .investmentStage(profile.getInvestmentStage())
                .portfolioCompanies(profile.getPortfolioCompanies())
                .totalInvestments(profile.getTotalInvestments())
                .minimumInvestment(profile.getMinimumInvestment())
                .maximumInvestment(profile.getMaximumInvestment())
                .build();
    }
}
