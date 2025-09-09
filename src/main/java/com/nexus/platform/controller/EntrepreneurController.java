package com.nexus.platform.controller;

import com.nexus.platform.dto.EntrepreneurDTO;
import com.nexus.platform.service.EntrepreneurService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/entrepreneurs")
@AllArgsConstructor
public class EntrepreneurController {

    private final EntrepreneurService entrepreneurService;

    @GetMapping
    public ResponseEntity<List<EntrepreneurDTO>> getAllEntrepreneurs(
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) List<String> industries,
            @RequestParam(required = false) String fundingRange) {
        return ResponseEntity.ok(entrepreneurService.getAllEntrepreneurs(searchQuery, industries, fundingRange));
    }
}
