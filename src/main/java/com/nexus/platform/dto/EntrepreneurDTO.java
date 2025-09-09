package com.nexus.platform.dto;

import com.nexus.platform.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntrepreneurDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private String avatarUrl;
    private String bio;
    private boolean isOnline;
    private LocalDateTime createdAt;
    private String startupName;
    private String pitchSummary;
    private BigDecimal fundingNeeded;
    private String industry;
    private String location;
    private Integer foundedYear;
    private Integer teamSize;
    private String website;
}
