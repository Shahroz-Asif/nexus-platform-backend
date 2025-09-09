package com.nexus.platform.controller;

import com.nexus.platform.service.VideoCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video")
public class VideoCallController {

    @Autowired
    private VideoCallService videoCallService;

    @PostMapping("/createRoom")
    public ResponseEntity<?> createRoom(@RequestParam Long meetingId) {
        return ResponseEntity.ok(videoCallService.createRoom(meetingId));
    }

    @PostMapping("/joinRoom")
    public ResponseEntity<?> joinRoom(@RequestParam String roomId) {
        videoCallService.joinRoom(roomId);
        return ResponseEntity.ok().build();
    }
}
