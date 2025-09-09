package com.nexus.platform.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class MeetingRequest {
    private String title;
    private Set<Long> participantIds;
    private LocalDateTime dateTime;
    private LocalDateTime endTime;
}
