package com.nexus.platform.controller;

import com.nexus.platform.dto.InvestorDTO;
import com.nexus.platform.service.InvestorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(InvestorController.class)
public class InvestorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvestorService investorService;

    @Test
    @WithMockUser
    void testGetAllInvestors() throws Exception {
        InvestorDTO investorDTO = InvestorDTO.builder().id(1L).name("Test Investor").build();
        when(investorService.getAllInvestors(any(), any(), any())).thenReturn(Collections.singletonList(investorDTO));

        mockMvc.perform(get("/investors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Investor"));
    }
}
