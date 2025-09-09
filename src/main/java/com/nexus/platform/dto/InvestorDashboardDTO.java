package com.nexus.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvestorDashboardDTO {
    private long totalStartups;
    private long totalIndustries;
    private int yourConnections;
}
