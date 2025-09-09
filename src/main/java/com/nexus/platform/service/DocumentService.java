package com.nexus.platform.service;

import com.nexus.platform.dto.DocumentDTO;
import com.nexus.platform.entity.Document;
import com.nexus.platform.entity.User;
import com.nexus.platform.exception.ResourceNotFoundException;
import com.nexus.platform.mapper.DocumentMapper;
import com.nexus.platform.repository.DocumentRepository;
import com.nexus.platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private final String cloudStorageUrl;
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    public DocumentService(@Value("${cloud.storage.url}") String cloudStorageUrl, DocumentRepository documentRepository, UserRepository userRepository) {
        this.cloudStorageUrl = cloudStorageUrl;
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }

    public DocumentDTO uploadDocument(MultipartFile file, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        String fileUrl = cloudStorageUrl + file.getOriginalFilename();
        Document document = Document.builder()
                .owner(user)
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .lastModified(LocalDateTime.now())
                .shared(false)
                .fileUrl(fileUrl)
                .build();
        return DocumentMapper.toDTO(documentRepository.save(document));
    }

    public DocumentDTO getDocument(Long id) {
        return DocumentMapper.toDTO(documentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Document", "id", id)));
    }

    public List<DocumentDTO> getAllDocumentsByUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return documentRepository.findByOwner(user).stream().map(DocumentMapper::toDTO).collect(Collectors.toList());
    }

    public DocumentDTO signDocument(Long id, String signature) {
        Document document = documentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Document", "id", id));
        document.setSignature(signature);
        return DocumentMapper.toDTO(documentRepository.save(document));
    }

    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }
}
