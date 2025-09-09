package com.nexus.platform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexus.platform.dto.PaymentRequest;
import com.nexus.platform.entity.Transaction;
import com.nexus.platform.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    private PaymentRequest paymentRequest;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(new BigDecimal("100"));
        paymentRequest.setRecipientId(2L);

        transaction = new Transaction();
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testDeposit() throws Exception {
        when(paymentService.deposit(any(PaymentRequest.class), anyString())).thenReturn(transaction);

        mockMvc.perform(post("/payments/deposit")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testWithdraw() throws Exception {
        when(paymentService.withdraw(any(PaymentRequest.class), anyString())).thenReturn(transaction);

        mockMvc.perform(post("/payments/withdraw")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testTransfer() throws Exception {
        when(paymentService.transfer(any(PaymentRequest.class), anyString())).thenReturn(transaction);

        mockMvc.perform(post("/payments/transfer")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void testGetHistory() throws Exception {
        when(paymentService.getTransactionHistory(anyString())).thenReturn(Collections.singletonList(transaction));

        mockMvc.perform(get("/payments/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }
}
