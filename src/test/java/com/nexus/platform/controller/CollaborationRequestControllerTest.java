package com.nexus.platform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexus.platform.dto.CollaborationRequestDTO;
import com.nexus.platform.entity.CollaborationStatus;
import com.nexus.platform.service.CollaborationRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CollaborationRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollaborationRequestService collaborationRequestService;

    @Autowired
    private ObjectMapper objectMapper;

    private CollaborationRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        requestDTO = CollaborationRequestDTO.builder()
                .id(1L)
                .investorId(1L)
                .entrepreneurId(2L)
                .message("Let's connect")
                .status(CollaborationStatus.PENDING)
                .build();
    }

    @Test
    @WithMockUser
    void testCreateRequest() throws Exception {
        when(collaborationRequestService.createRequest(any(Long.class), any(Long.class), any(String.class))).thenReturn(requestDTO);

        mockMvc.perform(post("/collaboration-requests")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Let's connect"));
    }

    @Test
    @WithMockUser
    void testCreateRequest_BadRequest() throws Exception {
        when(collaborationRequestService.createRequest(any(Long.class), any(Long.class), any(String.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/collaboration-requests")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser
    void testGetRequestsForInvestor() throws Exception {
        when(collaborationRequestService.getRequestsForInvestor(1L)).thenReturn(Collections.singletonList(requestDTO));

        mockMvc.perform(get("/collaboration-requests/investor/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].message").value("Let's connect"));
    }

    @Test
    @WithMockUser
    void testGetRequestsForEntrepreneur() throws Exception {
        when(collaborationRequestService.getRequestsForEntrepreneur(2L)).thenReturn(Collections.singletonList(requestDTO));

        mockMvc.perform(get("/collaboration-requests/entrepreneur/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].message").value("Let's connect"));
    }
}
