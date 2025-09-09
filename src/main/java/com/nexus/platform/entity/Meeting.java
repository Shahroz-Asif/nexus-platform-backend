package com.nexus.platform.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "meetings")
@Data
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToMany
    private Set<User> participants;
    private LocalDateTime dateTime;
    private LocalDateTime endTime;
    @Enumerated(EnumType.STRING)
    private MeetingStatus status;
    private String videoRoomId;

    public static Meeting createMeeting(String title, LocalDateTime dateTime, LocalDateTime endTime, Set<User> participants) {
        Meeting meeting = new Meeting();
        meeting.setTitle(title);
        meeting.setDateTime(dateTime);
        meeting.setEndTime(endTime);
        meeting.setParticipants(participants);
        meeting.setStatus(MeetingStatus.SCHEDULED);
        return meeting;
    }
}
