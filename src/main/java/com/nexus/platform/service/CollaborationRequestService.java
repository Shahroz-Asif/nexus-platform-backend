package com.nexus.platform.service;

import com.nexus.platform.dto.CollaborationRequestDTO;
import com.nexus.platform.entity.CollaborationRequest;
import com.nexus.platform.entity.CollaborationStatus;
import com.nexus.platform.entity.User;
import com.nexus.platform.mapper.CollaborationRequestMapper;
import com.nexus.platform.repository.CollaborationRequestRepository;
import com.nexus.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollaborationRequestService {

    private final CollaborationRequestRepository collaborationRequestRepository;
    private final UserRepository userRepository;
    private final CollaborationRequestMapper collaborationRequestMapper;

    public CollaborationRequestDTO createRequest(Long investorId, Long entrepreneurId, String message) {
        User investor = userRepository.findById(investorId).orElseThrow(() -> new RuntimeException("Investor not found"));
        User entrepreneur = userRepository.findById(entrepreneurId).orElseThrow(() -> new RuntimeException("Entrepreneur not found"));

        CollaborationRequest request = CollaborationRequest.builder()
                .investor(investor)
                .entrepreneur(entrepreneur)
                .message(message)
                .status(CollaborationStatus.PENDING)
                .build();

        return collaborationRequestMapper.toDTO(collaborationRequestRepository.save(request));
    }

    public CollaborationRequestDTO updateRequestStatus(Long requestId, CollaborationStatus status) {
        CollaborationRequest request = collaborationRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(status);
        return collaborationRequestMapper.toDTO(collaborationRequestRepository.save(request));
    }

    public List<CollaborationRequestDTO> getRequestsForInvestor(Long investorId) {
        return collaborationRequestRepository.findByInvestorId(investorId).stream()
                .map(collaborationRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CollaborationRequestDTO> getRequestsForEntrepreneur(Long entrepreneurId) {
        return collaborationRequestRepository.findByEntrepreneurId(entrepreneurId).stream()
                .map(collaborationRequestMapper::toDTO)
                .collect(Collectors.toList());
    }
}
