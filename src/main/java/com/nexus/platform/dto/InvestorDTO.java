package com.nexus.platform.dto;

import com.nexus.platform.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvestorDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private String avatarUrl;
    private String bio;
    private boolean isOnline;
    private LocalDateTime createdAt;
    private List<String> investmentInterests;
    private List<String> investmentStage;
    private List<String> portfolioCompanies;
    private Integer totalInvestments;
    private BigDecimal minimumInvestment;
    private BigDecimal maximumInvestment;
}
