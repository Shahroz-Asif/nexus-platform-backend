package com.nexus.platform.service;

import com.nexus.platform.dto.InvestorDTO;
import com.nexus.platform.entity.InvestorProfile;
import com.nexus.platform.entity.User;
import com.nexus.platform.repository.InvestorProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InvestorServiceTest {

    @Mock
    private InvestorProfileRepository investorProfileRepository;

    @InjectMocks
    private InvestorService investorService;

    @Test
    void testGetAllInvestors() {
        User user = User.builder().id(1L).name("Test Investor").build();
        InvestorProfile profile = InvestorProfile.builder().id(1L).user(user).build();
        when(investorProfileRepository.findAll(any(Specification.class))).thenReturn(Collections.singletonList(profile));

        List<InvestorDTO> result = investorService.getAllInvestors(null, null, null);

        assertEquals(1, result.size());
        assertEquals("Test Investor", result.get(0).getName());
    }
}
