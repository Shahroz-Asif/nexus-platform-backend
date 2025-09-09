package com.nexus.platform.controller;

import com.nexus.platform.dto.DealDTO;
import com.nexus.platform.service.DealService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/deals")
@AllArgsConstructor
public class DealController {

    private final DealService dealService;

    @GetMapping
    public ResponseEntity<List<DealDTO>> getAllDeals() {
        return ResponseEntity.ok(dealService.getAllDeals());
    }
}
