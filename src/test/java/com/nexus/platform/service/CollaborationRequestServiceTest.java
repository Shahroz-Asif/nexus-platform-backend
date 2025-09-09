package com.nexus.platform.service;

import com.nexus.platform.dto.CollaborationRequestDTO;
import com.nexus.platform.entity.CollaborationRequest;
import com.nexus.platform.entity.CollaborationStatus;
import com.nexus.platform.entity.User;
import com.nexus.platform.mapper.CollaborationRequestMapper;
import com.nexus.platform.repository.CollaborationRequestRepository;
import com.nexus.platform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CollaborationRequestServiceTest {

    @Mock
    private CollaborationRequestRepository collaborationRequestRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CollaborationRequestMapper collaborationRequestMapper;

    @InjectMocks
    private CollaborationRequestService collaborationRequestService;

    private User investor;
    private User entrepreneur;
    private CollaborationRequestDTO requestDTO;
    private CollaborationRequest request;

    @BeforeEach
    void setUp() {
        investor = User.builder().id(1L).build();
        entrepreneur = User.builder().id(2L).build();

        requestDTO = CollaborationRequestDTO.builder()
                .investorId(1L)
                .entrepreneurId(2L)
                .message("Let's connect")
                .status(CollaborationStatus.PENDING)
                .build();

        request = CollaborationRequest.builder()
                .id(1L)
                .investor(investor)
                .entrepreneur(entrepreneur)
                .message("Let's connect")
                .status(CollaborationStatus.PENDING)
                .build();
    }

    @Test
    void testCreateRequest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(investor));
        when(userRepository.findById(2L)).thenReturn(Optional.of(entrepreneur));
        when(collaborationRequestRepository.save(any(CollaborationRequest.class))).thenReturn(request);
        when(collaborationRequestMapper.toDTO(any(CollaborationRequest.class))).thenReturn(requestDTO);

        CollaborationRequestDTO createdRequest = collaborationRequestService.createRequest(1L, 2L, "Let's connect");

        assertNotNull(createdRequest);
        assertEquals(requestDTO.getInvestorId(), createdRequest.getInvestorId());
        verify(collaborationRequestRepository, times(1)).save(any(CollaborationRequest.class));
    }

    @Test
    void testCreateRequest_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            collaborationRequestService.createRequest(1L, 2L, "Let's connect");
        });

        verify(collaborationRequestRepository, never()).save(any(CollaborationRequest.class));
    }

    @Test
    void testGetRequestsForInvestor() {
        when(collaborationRequestRepository.findByInvestorId(1L)).thenReturn(Collections.singletonList(request));

        List<CollaborationRequestDTO> requests = collaborationRequestService.getRequestsForInvestor(1L);

        assertFalse(requests.isEmpty());
        assertEquals(1, requests.size());
        verify(collaborationRequestRepository, times(1)).findByInvestorId(1L);
    }

    @Test
    void testGetRequestsForEntrepreneur() {
        when(collaborationRequestRepository.findByEntrepreneurId(2L)).thenReturn(Collections.singletonList(request));

        List<CollaborationRequestDTO> requests = collaborationRequestService.getRequestsForEntrepreneur(2L);

        assertFalse(requests.isEmpty());
        assertEquals(1, requests.size());
        verify(collaborationRequestRepository, times(1)).findByEntrepreneurId(2L);
    }
}
