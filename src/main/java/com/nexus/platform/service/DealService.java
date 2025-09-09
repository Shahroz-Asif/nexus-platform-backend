package com.nexus.platform.service;

import com.nexus.platform.dto.DealDTO;
import com.nexus.platform.repository.DealRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DealService {

    private final DealRepository dealRepository;
    private final ModelMapper modelMapper;

    public List<DealDTO> getAllDeals() {
        return dealRepository.findAll().stream()
                .map(deal -> modelMapper.map(deal, DealDTO.class))
                .collect(Collectors.toList());
    }
}
