package com.nexus.platform.mapper;

import com.nexus.platform.dto.DocumentDTO;
import com.nexus.platform.entity.Document;

public class DocumentMapper {

    public static DocumentDTO toDTO(Document document) {
        return DocumentDTO.builder()
                .id(document.getId())
                .name(document.getName())
                .type(document.getType())
                .size(document.getSize())
                .lastModified(document.getLastModified())
                .shared(document.isShared())
                .fileUrl(document.getFileUrl())
                .signature(document.getSignature())
                .ownerName(document.getOwner().getName())
                .build();
    }

    public static Document toEntity(DocumentDTO documentDTO) {
        return Document.builder()
                .id(documentDTO.getId())
                .name(documentDTO.getName())
                .type(documentDTO.getType())
                .size(documentDTO.getSize())
                .lastModified(documentDTO.getLastModified())
                .shared(documentDTO.isShared())
                .fileUrl(documentDTO.getFileUrl())
                .signature(documentDTO.getSignature())
                .build();
    }
}
