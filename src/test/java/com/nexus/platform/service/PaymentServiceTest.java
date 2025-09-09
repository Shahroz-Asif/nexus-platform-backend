package com.nexus.platform.service;

import com.nexus.platform.dto.PaymentRequest;
import com.nexus.platform.entity.Role;
import com.nexus.platform.entity.Transaction;
import com.nexus.platform.entity.TransactionType;
import com.nexus.platform.entity.User;
import com.nexus.platform.repository.TransactionRepository;
import com.nexus.platform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private PaymentService paymentService;

    private User user;
    private User recipient;
    private PaymentRequest paymentRequest;

    @BeforeEach
    void setUp() {
        user = User.builder().email("sender@example.com").password("password").role(Role.INVESTOR).build();
        user.setId(1L);
        user.setBalance(new BigDecimal("1000"));

        recipient = User.builder().email("recipient@example.com").password("password").role(Role.ENTREPRENEUR).build();
        recipient.setId(2L);
        recipient.setBalance(new BigDecimal("500"));

        paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(new BigDecimal("100"));
        paymentRequest.setRecipientId(2L);
    }

    @Test
    void testDeposit() {
        when(userRepository.findByEmail("sender@example.com")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArguments()[0]);

        Transaction transaction = paymentService.deposit(paymentRequest, "sender@example.com");

        assertEquals(new BigDecimal("1100"), user.getBalance());
        assertEquals(TransactionType.DEPOSIT, transaction.getType());
    }

    @Test
    void testWithdraw_Success() {
        when(userRepository.findByEmail("sender@example.com")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArguments()[0]);

        Transaction transaction = paymentService.withdraw(paymentRequest, "sender@example.com");

        assertEquals(new BigDecimal("900"), user.getBalance());
        assertEquals(TransactionType.WITHDRAW, transaction.getType());
    }

    @Test
    void testWithdraw_InsufficientFunds() {
        user.setBalance(new BigDecimal("50"));
        when(userRepository.findByEmail("sender@example.com")).thenReturn(Optional.of(user));

        assertThrows(IllegalStateException.class, () -> paymentService.withdraw(paymentRequest, "sender@example.com"));
    }

    @Test
    void testTransfer_Success() {
        when(userRepository.findByEmail("sender@example.com")).thenReturn(Optional.of(user));
        when(userRepository.findById(2L)).thenReturn(Optional.of(recipient));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArguments()[0]);

        Transaction transaction = paymentService.transfer(paymentRequest, "sender@example.com");

        assertEquals(new BigDecimal("900"), user.getBalance());
        assertEquals(new BigDecimal("600"), recipient.getBalance());
        assertEquals(TransactionType.TRANSFER, transaction.getType());
    }

    @Test
    void testTransfer_InsufficientFunds() {
        user.setBalance(new BigDecimal("50"));
        when(userRepository.findByEmail("sender@example.com")).thenReturn(Optional.of(user));
        when(userRepository.findById(2L)).thenReturn(Optional.of(recipient));

        assertThrows(IllegalStateException.class, () -> paymentService.transfer(paymentRequest, "sender@example.com"));
    }
}
