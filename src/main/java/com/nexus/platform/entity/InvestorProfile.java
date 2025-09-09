package com.nexus.platform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "investor_profiles")
public class InvestorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ElementCollection
    @CollectionTable(name = "investor_interests", joinColumns = @JoinColumn(name = "investor_id"))
    @Column(name = "interest")
    private List<String> investmentInterests;

    @ElementCollection
    @CollectionTable(name = "investor_stages", joinColumns = @JoinColumn(name = "investor_id"))
    @Column(name = "stage")
    private List<String> investmentStage;

    @ElementCollection
    @CollectionTable(name = "investor_portfolio", joinColumns = @JoinColumn(name = "investor_id"))
    @Column(name = "company")
    private List<String> portfolioCompanies;

    private Integer totalInvestments;

    private BigDecimal minimumInvestment;

    private BigDecimal maximumInvestment;
}
