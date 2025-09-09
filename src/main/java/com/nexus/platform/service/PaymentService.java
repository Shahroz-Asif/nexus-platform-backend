package com.nexus.platform.service;

import com.nexus.platform.dto.PaymentRequest;
import com.nexus.platform.entity.Transaction;
import com.nexus.platform.entity.TransactionStatus;
import com.nexus.platform.entity.TransactionType;
import com.nexus.platform.entity.User;
import com.nexus.platform.exception.ResourceNotFoundException;
import com.nexus.platform.repository.TransactionRepository;
import com.nexus.platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Transaction deposit(PaymentRequest paymentRequest, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        user.setBalance(user.getBalance().add(paymentRequest.getAmount()));
        userRepository.save(user);

        Transaction transaction = Transaction.createTransaction(user, paymentRequest.getAmount(), TransactionType.DEPOSIT);
        transaction.setStatus(TransactionStatus.COMPLETED);
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction withdraw(PaymentRequest paymentRequest, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        if (user.getBalance().compareTo(paymentRequest.getAmount()) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }
        user.setBalance(user.getBalance().subtract(paymentRequest.getAmount()));
        userRepository.save(user);

        Transaction transaction = Transaction.createTransaction(user, paymentRequest.getAmount(), TransactionType.WITHDRAW);
        transaction.setStatus(TransactionStatus.COMPLETED);
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction transfer(PaymentRequest paymentRequest, String email) {
        User sender = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        User recipient = userRepository.findById(paymentRequest.getRecipientId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", paymentRequest.getRecipientId()));

        if (sender.getBalance().compareTo(paymentRequest.getAmount()) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }

        sender.setBalance(sender.getBalance().subtract(paymentRequest.getAmount()));
        recipient.setBalance(recipient.getBalance().add(paymentRequest.getAmount()));

        userRepository.save(sender);
        userRepository.save(recipient);

        Transaction transaction = Transaction.createTransaction(sender, paymentRequest.getAmount(), TransactionType.TRANSFER);
        transaction.setStatus(TransactionStatus.COMPLETED);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionHistory(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return transactionRepository.findByUser(user);
    }
}
