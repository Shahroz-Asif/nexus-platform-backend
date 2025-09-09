package com.nexus.platform.service;

import com.nexus.platform.dto.MeetingRequest;
import com.nexus.platform.entity.Meeting;
import com.nexus.platform.entity.MeetingStatus;
import com.nexus.platform.entity.Role;
import com.nexus.platform.entity.User;
import com.nexus.platform.exception.ResourceNotFoundException;
import com.nexus.platform.repository.MeetingRepository;
import com.nexus.platform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MeetingServiceTest {

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MeetingService meetingService;

    private MeetingRequest meetingRequest;
    private User user1;
    private User user2;
    private Set<User> participants;
    private Meeting meeting;

    @BeforeEach
    void setUp() {
        user1 = User.builder().email("user1@example.com").password("pass").role(Role.INVESTOR).build();
        user1.setId(1L);
        user2 = User.builder().email("user2@example.com").password("pass").role(Role.ENTREPRENEUR).build();
        user2.setId(2L);
        participants = new HashSet<>();
        participants.add(user1);
        participants.add(user2);

        meetingRequest = new MeetingRequest();
        meetingRequest.setTitle("Test Meeting");
        meetingRequest.setDateTime(LocalDateTime.now().plusDays(1));
        meetingRequest.setEndTime(LocalDateTime.now().plusDays(1).plusHours(1));
        meetingRequest.setParticipantIds(Set.of(1L, 2L));

        meeting = Meeting.createMeeting("Test Meeting", meetingRequest.getDateTime(), meetingRequest.getEndTime(), participants);
    }

    @Test
    void testCreateMeeting_Success() {
        when(userRepository.findAllById(meetingRequest.getParticipantIds())).thenReturn(Collections.list(Collections.enumeration(participants)));
        when(meetingRepository.findOverlappingMeetings(any(), any(), any())).thenReturn(Collections.emptyList());
        when(meetingRepository.save(any(Meeting.class))).thenReturn(meeting);

        Meeting createdMeeting = meetingService.createMeeting(meetingRequest);

        assertNotNull(createdMeeting);
        assertEquals("Test Meeting", createdMeeting.getTitle());
    }

    @Test
    void testCreateMeeting_Overlapping() {
        when(userRepository.findAllById(meetingRequest.getParticipantIds())).thenReturn(Collections.list(Collections.enumeration(participants)));
        when(meetingRepository.findOverlappingMeetings(any(), any(), any())).thenReturn(Collections.singletonList(new Meeting()));

        assertThrows(IllegalStateException.class, () -> meetingService.createMeeting(meetingRequest));
    }

    @Test
    void testUpdateMeeting_Success() {
        when(meetingRepository.findById(1L)).thenReturn(Optional.of(meeting));
        when(userRepository.findAllById(meetingRequest.getParticipantIds())).thenReturn(Collections.list(Collections.enumeration(participants)));
        when(meetingRepository.findOverlappingMeetingsForUpdate(any(), any(), any(), any())).thenReturn(Collections.emptyList());
        when(meetingRepository.save(any(Meeting.class))).thenReturn(meeting);

        Meeting updatedMeeting = meetingService.updateMeeting(1L, meetingRequest);

        assertNotNull(updatedMeeting);
    }

    @Test
    void testCancelMeeting() {
        when(meetingRepository.findById(1L)).thenReturn(Optional.of(meeting));
        when(meetingRepository.save(any(Meeting.class))).thenReturn(meeting);

        meetingService.cancelMeeting(1L);

        assertEquals(MeetingStatus.CANCELED, meeting.getStatus());
    }

    @Test
    void testUpdateMeeting_NotFound() {
        when(meetingRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> meetingService.updateMeeting(1L, meetingRequest));
    }
}
