package com.nexus.platform.service;

import com.nexus.platform.dto.DashboardDTO;
import com.nexus.platform.dto.InvestorDashboardDTO;
import com.nexus.platform.entity.CollaborationStatus;
import com.nexus.platform.entity.User;
import com.nexus.platform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final CollaborationRequestRepository collaborationRequestRepository;
    private final MeetingRepository meetingRepository;
    private final ProfileViewRepository profileViewRepository;
    private final UserRepository userRepository;
    private final StartupRepository startupRepository;

    public DashboardDTO getEntrepreneurDashboard(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        int pendingRequests = collaborationRequestRepository.findByEntrepreneurAndStatus(user, CollaborationStatus.PENDING).size();
        int totalConnections = collaborationRequestRepository.findByEntrepreneurAndStatus(user, CollaborationStatus.ACCEPTED).size();
        int upcomingMeetings = meetingRepository.findByParticipantsContainsAndDateTimeAfter(user, LocalDateTime.now()).size();
        int profileViews = profileViewRepository.countByViewedProfileId(userId);

        return DashboardDTO.builder()
                .pendingRequests(pendingRequests)
                .totalConnections(totalConnections)
                .upcomingMeetings(upcomingMeetings)
                .profileViews(profileViews)
                .build();
    }

    public InvestorDashboardDTO getInvestorDashboard(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        long totalStartups = startupRepository.count();
        long totalIndustries = startupRepository.countDistinctIndustry();
        int yourConnections = collaborationRequestRepository.findByInvestorAndStatus(user, CollaborationStatus.ACCEPTED).size();

        return InvestorDashboardDTO.builder()
                .totalStartups(totalStartups)
                .totalIndustries(totalIndustries)
                .yourConnections(yourConnections)
                .build();
    }
}
