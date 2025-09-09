package com.nexus.platform.controller;

import com.nexus.platform.dto.DashboardDTO;
import com.nexus.platform.dto.InvestorDashboardDTO;
import com.nexus.platform.entity.User;
import com.nexus.platform.repository.UserRepository;
import com.nexus.platform.security.JwtTokenUtil;
import com.nexus.platform.security.SecurityConfig;
import com.nexus.platform.service.DashboardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DashboardController.class)
@Import(SecurityConfig.class)
public class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DashboardService dashboardService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @Test
    @WithMockUser(username = "test@example.com")
    void testGetEntrepreneurDashboard() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        DashboardDTO dashboardDTO = DashboardDTO.builder()
                .pendingRequests(5)
                .totalConnections(10)
                .upcomingMeetings(2)
                .profileViews(24)
                .build();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(dashboardService.getEntrepreneurDashboard(anyLong())).thenReturn(dashboardDTO);

        mockMvc.perform(get("/dashboard/entrepreneur"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pendingRequests").value(5))
                .andExpect(jsonPath("$.totalConnections").value(10))
                .andExpect(jsonPath("$.upcomingMeetings").value(2))
                .andExpect(jsonPath("$.profileViews").value(24));
    }

    @Test
    @WithMockUser(username = "investor@example.com")
    void testGetInvestorDashboard() throws Exception {
        User user = new User();
        user.setId(2L);
        user.setEmail("investor@example.com");

        InvestorDashboardDTO investorDashboardDTO = InvestorDashboardDTO.builder()
                .totalStartups(50)
                .totalIndustries(12)
                .yourConnections(8)
                .build();

        when(userRepository.findByEmail("investor@example.com")).thenReturn(Optional.of(user));
        when(dashboardService.getInvestorDashboard(anyLong())).thenReturn(investorDashboardDTO);

        mockMvc.perform(get("/dashboard/investor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalStartups").value(50))
                .andExpect(jsonPath("$.totalIndustries").value(12))
                .andExpect(jsonPath("$.yourConnections").value(8));
    }
}
