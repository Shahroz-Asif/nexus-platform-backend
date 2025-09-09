package com.nexus.platform.service;

import com.nexus.platform.entity.Meeting;
import com.nexus.platform.exception.ResourceNotFoundException;
import com.nexus.platform.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VideoCallService {

    @Autowired
    private MeetingRepository meetingRepository;

    public String createRoom(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting", "id", meetingId));
        String roomId = UUID.randomUUID().toString();
        meeting.setVideoRoomId(roomId);
        meetingRepository.save(meeting);
        return roomId;
    }

    public void joinRoom(String roomId) {
        // In a real application, you might add logic here to validate the room
        // or notify other participants. For now, we'll just log the action.
        System.out.println("User joined room: " + roomId);
    }
}
