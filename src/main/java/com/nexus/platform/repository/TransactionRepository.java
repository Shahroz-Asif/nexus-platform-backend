package com.nexus.platform.repository;

import com.nexus.platform.entity.Transaction;
import com.nexus.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
}
