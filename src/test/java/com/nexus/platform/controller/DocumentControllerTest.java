package com.nexus.platform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexus.platform.dto.DocumentDTO;
import com.nexus.platform.dto.SignRequest;
import com.nexus.platform.entity.Role;
import com.nexus.platform.entity.User;
import com.nexus.platform.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentService documentService;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMultipartFile file;
    private DocumentDTO documentDTO;
    private User user;

    @BeforeEach
    void setUp() {
        file = new MockMultipartFile("file", "test.pdf", "application/pdf", "test data".getBytes());
        user = User.builder().id(1L).name("Test User").email("test@example.com").password("password").role(Role.INVESTOR).build();
        documentDTO = DocumentDTO.builder()
                .id(1L)
                .ownerName(user.getName())
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .lastModified(LocalDateTime.now())
                .shared(false)
                .fileUrl("http://localhost:8080/test.pdf")
                .build();
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testUploadDocument() throws Exception {
        when(documentService.uploadDocument(any(), anyString())).thenReturn(documentDTO);

        mockMvc.perform(multipart("/documents/upload")
                        .file(file)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileUrl").value("http://localhost:8080/test.pdf"));
    }

    @Test
    @WithMockUser
    void testSignDocument() throws Exception {
        SignRequest signRequest = new SignRequest();
        signRequest.setSignature("test_signature");

        when(documentService.signDocument(anyLong(), anyString())).thenReturn(documentDTO);

        mockMvc.perform(post("/documents/1/sign")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signRequest)))
                .andExpect(status().isOk());
    }
}
