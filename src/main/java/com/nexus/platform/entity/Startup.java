package com.nexus.platform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "startups")
public class Startup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "startup_name")
    private String startupName;

    @Column(columnDefinition = "TEXT")
    private String pitchSummary;

    private BigDecimal fundingNeeded;

    private String industry;

    private String location;

    private Integer foundedYear;

    private Integer teamSize;

    private String website;
}
