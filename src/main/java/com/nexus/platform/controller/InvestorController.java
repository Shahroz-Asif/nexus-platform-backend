package com.nexus.platform.controller;

import com.nexus.platform.dto.InvestorDTO;
import com.nexus.platform.service.InvestorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/investors")
@AllArgsConstructor
public class InvestorController {

    private final InvestorService investorService;

    @GetMapping
    public ResponseEntity<List<InvestorDTO>> getAllInvestors(
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) List<String> stages,
            @RequestParam(required = false) List<String> interests) {
        return ResponseEntity.ok(investorService.getAllInvestors(searchQuery, stages, interests));
    }
}
