package com.nexus.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentDTO {
    private Long id;
    private String name;
    private String type;
    private long size;
    private LocalDateTime lastModified;
    private boolean shared;
    private String fileUrl;
    private String signature;
    private String ownerName;
}
