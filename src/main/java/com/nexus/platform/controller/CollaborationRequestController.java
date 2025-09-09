package com.nexus.platform.controller;

import com.nexus.platform.dto.CollaborationRequestDTO;
import com.nexus.platform.entity.CollaborationStatus;
import com.nexus.platform.service.CollaborationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collaboration-requests")
@RequiredArgsConstructor
public class CollaborationRequestController {

    private final CollaborationRequestService collaborationRequestService;

    @PostMapping
    public ResponseEntity<CollaborationRequestDTO> createRequest(@RequestBody CollaborationRequestDTO requestDTO) {
        CollaborationRequestDTO createdRequest = collaborationRequestService.createRequest(
                requestDTO.getInvestorId(),
                requestDTO.getEntrepreneurId(),
                requestDTO.getMessage()
        );
        return ResponseEntity.ok(createdRequest);
    }

    @PatchMapping("/{requestId}")
    public ResponseEntity<CollaborationRequestDTO> updateRequestStatus(
            @PathVariable Long requestId,
            @RequestBody Map<String, String> body) {
        CollaborationStatus status = CollaborationStatus.valueOf(body.get("status").toUpperCase());
        CollaborationRequestDTO updatedRequest = collaborationRequestService.updateRequestStatus(requestId, status);
        return ResponseEntity.ok(updatedRequest);
    }

    @GetMapping("/investor/{investorId}")
    public ResponseEntity<List<CollaborationRequestDTO>> getRequestsForInvestor(@PathVariable Long investorId) {
        return ResponseEntity.ok(collaborationRequestService.getRequestsForInvestor(investorId));
    }

    @GetMapping("/entrepreneur/{entrepreneurId}")
    public ResponseEntity<List<CollaborationRequestDTO>> getRequestsForEntrepreneur(@PathVariable Long entrepreneurId) {
        return ResponseEntity.ok(collaborationRequestService.getRequestsForEntrepreneur(entrepreneurId));
    }
}
