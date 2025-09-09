package com.nexus.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {
    private int pendingRequests;
    private int totalConnections;
    private int upcomingMeetings;
    private int profileViews;
}
