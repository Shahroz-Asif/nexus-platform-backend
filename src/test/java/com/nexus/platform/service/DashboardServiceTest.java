package com.nexus.platform.service;

import com.nexus.platform.dto.DashboardDTO;
import com.nexus.platform.dto.InvestorDashboardDTO;
import com.nexus.platform.entity.CollaborationStatus;
import com.nexus.platform.entity.User;
import com.nexus.platform.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DashboardServiceTest {

    @InjectMocks
    private DashboardService dashboardService;

    @Mock
    private CollaborationRequestRepository collaborationRequestRepository;

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private ProfileViewRepository profileViewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StartupRepository startupRepository;

    @Test
    void testGetEntrepreneurDashboard() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(collaborationRequestRepository.findByEntrepreneurAndStatus(user, CollaborationStatus.PENDING)).thenReturn(Collections.nCopies(5, null));
        when(collaborationRequestRepository.findByEntrepreneurAndStatus(user, CollaborationStatus.ACCEPTED)).thenReturn(Collections.nCopies(10, null));
        when(meetingRepository.findByParticipantsContainsAndDateTimeAfter(any(User.class), any(LocalDateTime.class))).thenReturn(Collections.nCopies(2, null));
        when(profileViewRepository.countByViewedProfileId(anyLong())).thenReturn(24);

        DashboardDTO dashboardDTO = dashboardService.getEntrepreneurDashboard(1L);

        assertEquals(5, dashboardDTO.getPendingRequests());
        assertEquals(10, dashboardDTO.getTotalConnections());
        assertEquals(2, dashboardDTO.getUpcomingMeetings());
        assertEquals(24, dashboardDTO.getProfileViews());
    }

    @Test
    void testGetInvestorDashboard() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(startupRepository.count()).thenReturn(50L);
        when(startupRepository.countDistinctIndustry()).thenReturn(12L);
        when(collaborationRequestRepository.findByInvestorAndStatus(user, CollaborationStatus.ACCEPTED)).thenReturn(Collections.nCopies(8, null));

        InvestorDashboardDTO investorDashboardDTO = dashboardService.getInvestorDashboard(1L);

        assertEquals(50, investorDashboardDTO.getTotalStartups());
        assertEquals(12, investorDashboardDTO.getTotalIndustries());
        assertEquals(8, investorDashboardDTO.getYourConnections());
    }
}
