package com.nexus.platform.dto;

import com.nexus.platform.entity.DealStage;
import com.nexus.platform.entity.DealStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealDTO {
    private Long id;
    private StartupDTO startup;
    private UserProfile investor;
    private BigDecimal amount;
    private String equity;
    private DealStatus status;
    private DealStage stage;
    private LocalDate lastActivity;
}
