package com.nexus.platform.service;

import com.nexus.platform.dto.MeetingRequest;
import com.nexus.platform.entity.Meeting;
import com.nexus.platform.entity.MeetingStatus;
import com.nexus.platform.entity.User;
import com.nexus.platform.exception.ResourceNotFoundException;
import com.nexus.platform.repository.MeetingRepository;
import com.nexus.platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private UserRepository userRepository;

    public Meeting createMeeting(MeetingRequest meetingRequest) {
        Set<User> participants = new HashSet<>(userRepository.findAllById(meetingRequest.getParticipantIds()));
        if (!meetingRepository.findOverlappingMeetings(participants, meetingRequest.getDateTime(), meetingRequest.getEndTime()).isEmpty()) {
            throw new IllegalStateException("One or more participants have an overlapping meeting.");
        }
        Meeting meeting = Meeting.createMeeting(
                meetingRequest.getTitle(),
                meetingRequest.getDateTime(),
                meetingRequest.getEndTime(),
                participants
        );
        return meetingRepository.save(meeting);
    }

    public Meeting updateMeeting(Long id, MeetingRequest meetingRequest) {
        Meeting meeting = meetingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Meeting", "id", id));
        Set<User> participants = new HashSet<>(userRepository.findAllById(meetingRequest.getParticipantIds()));
        if (!meetingRepository.findOverlappingMeetingsForUpdate(participants, meetingRequest.getDateTime(), meetingRequest.getEndTime(), id).isEmpty()) {
            throw new IllegalStateException("One or more participants have an overlapping meeting.");
        }
        meeting.setTitle(meetingRequest.getTitle());
        meeting.setDateTime(meetingRequest.getDateTime());
        meeting.setEndTime(meetingRequest.getEndTime());
        meeting.setParticipants(participants);
        return meetingRepository.save(meeting);
    }

    public void cancelMeeting(Long id) {
        Meeting meeting = meetingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Meeting", "id", id));
        meeting.setStatus(MeetingStatus.CANCELED);
        meetingRepository.save(meeting);
    }
}
