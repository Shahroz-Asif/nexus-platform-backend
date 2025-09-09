package com.nexus.platform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexus.platform.dto.MeetingRequest;
import com.nexus.platform.entity.Meeting;
import com.nexus.platform.service.MeetingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MeetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeetingService meetingService;

    @Autowired
    private ObjectMapper objectMapper;

    private MeetingRequest meetingRequest;
    private Meeting meeting;

    @BeforeEach
    void setUp() {
        meetingRequest = new MeetingRequest();
        meetingRequest.setTitle("Test Meeting");
        meetingRequest.setDateTime(LocalDateTime.now().plusDays(1));
        meetingRequest.setEndTime(LocalDateTime.now().plusDays(1).plusHours(1));
        meetingRequest.setParticipantIds(Set.of(1L, 2L));

        meeting = Meeting.createMeeting(
                "Test Meeting",
                meetingRequest.getDateTime(),
                meetingRequest.getEndTime(),
                Collections.emptySet()
        );
    }

    @Test
    @WithMockUser
    void testCreateMeeting() throws Exception {
        when(meetingService.createMeeting(any(MeetingRequest.class))).thenReturn(meeting);

        mockMvc.perform(post("/meetings")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meetingRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Meeting"));
    }

    @Test
    @WithMockUser
    void testUpdateMeeting() throws Exception {
        when(meetingService.updateMeeting(anyLong(), any(MeetingRequest.class))).thenReturn(meeting);

        mockMvc.perform(put("/meetings/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meetingRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Meeting"));
    }

    @Test
    @WithMockUser
    void testCancelMeeting() throws Exception {
        doNothing().when(meetingService).cancelMeeting(anyLong());

        mockMvc.perform(delete("/meetings/1").with(csrf()))
                .andExpect(status().isOk());
    }
}
