package com.nexus.platform.controller;

import com.nexus.platform.dto.PaymentRequest;
import com.nexus.platform.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody PaymentRequest paymentRequest, Principal principal) {
        return ResponseEntity.ok(paymentService.deposit(paymentRequest, principal.getName()));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody PaymentRequest paymentRequest, Principal principal) {
        return ResponseEntity.ok(paymentService.withdraw(paymentRequest, principal.getName()));
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody PaymentRequest paymentRequest, Principal principal) {
        return ResponseEntity.ok(paymentService.transfer(paymentRequest, principal.getName()));
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(Principal principal) {
        return ResponseEntity.ok(paymentService.getTransactionHistory(principal.getName()));
    }
}
