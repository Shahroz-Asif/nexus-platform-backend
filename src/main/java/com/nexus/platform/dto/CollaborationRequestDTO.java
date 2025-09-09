package com.nexus.platform.dto;

import com.nexus.platform.entity.CollaborationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollaborationRequestDTO {
    private Long id;
    private Long investorId;
    private String investorName;
    private Long entrepreneurId;
    private String entrepreneurName;
    private String startupName;
    private String message;
    private CollaborationStatus status;
}
