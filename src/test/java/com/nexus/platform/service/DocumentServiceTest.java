package com.nexus.platform.service;

import com.nexus.platform.dto.DocumentDTO;
import com.nexus.platform.entity.Document;
import com.nexus.platform.entity.Role;
import com.nexus.platform.entity.User;
import com.nexus.platform.exception.ResourceNotFoundException;
import com.nexus.platform.mapper.DocumentMapper;
import com.nexus.platform.repository.DocumentRepository;
import com.nexus.platform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DocumentService documentService;

    private User user;
    private Document document;
    private DocumentDTO documentDTO;
    private MockMultipartFile file;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).name("Test User").email("test@example.com").password("password").role(Role.INVESTOR).build();
        file = new MockMultipartFile("file", "test.pdf", "application/pdf", "test data".getBytes());
        document = Document.builder()
                .id(1L)
                .owner(user)
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .lastModified(LocalDateTime.now())
                .shared(false)
                .fileUrl("http://localhost:8080/test.pdf")
                .build();
        documentDTO = DocumentMapper.toDTO(document);
        ReflectionTestUtils.setField(documentService, "cloudStorageUrl", "http://localhost:8080/");
    }

    @Test
    void testUploadDocument() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(documentRepository.save(any(Document.class))).thenReturn(document);

        DocumentDTO uploadedDocument = documentService.uploadDocument(file, "test@example.com");

        assertNotNull(uploadedDocument);
        assertEquals("http://localhost:8080/test.pdf", uploadedDocument.getFileUrl());
    }

    @Test
    void testGetDocument_Success() {
        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));
        DocumentDTO foundDocument = documentService.getDocument(1L);
        assertNotNull(foundDocument);
        assertEquals(document.getFileUrl(), foundDocument.getFileUrl());
    }

    @Test
    void testGetDocument_NotFound() {
        when(documentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> documentService.getDocument(1L));
    }

    @Test
    void testSignDocument() {
        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));
        when(documentRepository.save(any(Document.class))).thenAnswer(i -> i.getArguments()[0]);

        DocumentDTO signedDocument = documentService.signDocument(1L, "test_signature");

        assertNotNull(signedDocument);
        assertEquals("test_signature", signedDocument.getSignature());
    }
}
