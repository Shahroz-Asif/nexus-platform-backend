package com.nexus.platform.controller;

import com.nexus.platform.dto.DocumentDTO;
import com.nexus.platform.dto.SignRequest;
import com.nexus.platform.service.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/documents")
@AllArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<DocumentDTO> uploadDocument(@RequestParam("file") MultipartFile file, Principal principal) {
        return ResponseEntity.ok(documentService.uploadDocument(file, principal.getName()));
    }

    @GetMapping
    public ResponseEntity<List<DocumentDTO>> getAllDocuments(Principal principal) {
        return ResponseEntity.ok(documentService.getAllDocumentsByUser(principal.getName()));
    }

    @GetMapping("/{id}/preview")
    public ResponseEntity<?> previewDocument(@PathVariable Long id) {
        DocumentDTO document = documentService.getDocument(id);
        try {
            UrlResource resource = new UrlResource(document.getFileUrl());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/sign")
    public ResponseEntity<DocumentDTO> signDocument(@PathVariable Long id, @RequestBody SignRequest signRequest) {
        return ResponseEntity.ok(documentService.signDocument(id, signRequest.getSignature()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok().build();
    }
}
