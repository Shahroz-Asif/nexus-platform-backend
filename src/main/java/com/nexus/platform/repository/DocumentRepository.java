package com.nexus.platform.repository;

import com.nexus.platform.entity.Document;
import com.nexus.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByOwner(User user);
}
