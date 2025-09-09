package com.nexus.platform.controller;

import com.nexus.platform.dto.MeetingRequest;
import com.nexus.platform.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @PostMapping
    public ResponseEntity<?> createMeeting(@RequestBody MeetingRequest meetingRequest) {
        return ResponseEntity.ok(meetingService.createMeeting(meetingRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMeeting(@PathVariable Long id, @RequestBody MeetingRequest meetingRequest) {
        return ResponseEntity.ok(meetingService.updateMeeting(id, meetingRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelMeeting(@PathVariable Long id) {
        meetingService.cancelMeeting(id);
        return ResponseEntity.ok().build();
    }
}
