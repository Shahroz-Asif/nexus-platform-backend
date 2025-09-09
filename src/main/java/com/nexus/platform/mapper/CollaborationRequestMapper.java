package com.nexus.platform.mapper;

import com.nexus.platform.dto.CollaborationRequestDTO;
import com.nexus.platform.entity.CollaborationRequest;
import com.nexus.platform.entity.Startup;
import com.nexus.platform.repository.StartupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CollaborationRequestMapper {

    private final StartupRepository startupRepository;

    public CollaborationRequestDTO toDTO(CollaborationRequest request) {
        Startup startup = startupRepository.findByUser(request.getEntrepreneur()).orElse(new Startup());
        return CollaborationRequestDTO.builder()
                .id(request.getId())
                .investorId(request.getInvestor().getId())
                .investorName(request.getInvestor().getName())
                .entrepreneurId(request.getEntrepreneur().getId())
                .entrepreneurName(request.getEntrepreneur().getName())
                .startupName(startup.getStartupName())
                .message(request.getMessage())
                .status(request.getStatus())
                .build();
    }
}
