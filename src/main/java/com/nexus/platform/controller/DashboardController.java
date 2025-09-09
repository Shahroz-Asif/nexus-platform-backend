package com.nexus.platform.controller;

import com.nexus.platform.dto.DashboardDTO;
import com.nexus.platform.dto.InvestorDashboardDTO;
import com.nexus.platform.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nexus.platform.entity.User;
import com.nexus.platform.repository.UserRepository;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final UserRepository userRepository;

    @GetMapping("/entrepreneur")
    public ResponseEntity<DashboardDTO> getEntrepreneurDashboard(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        DashboardDTO dashboardDTO = dashboardService.getEntrepreneurDashboard(user.getId());
        return ResponseEntity.ok(dashboardDTO);
    }

    @GetMapping("/investor")
    public ResponseEntity<InvestorDashboardDTO> getInvestorDashboard(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        InvestorDashboardDTO dashboardDTO = dashboardService.getInvestorDashboard(user.getId());
        return ResponseEntity.ok(dashboardDTO);
    }
}
