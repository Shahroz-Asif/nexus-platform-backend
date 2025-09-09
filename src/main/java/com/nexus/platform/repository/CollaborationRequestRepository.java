package com.nexus.platform.repository;

import com.nexus.platform.entity.CollaborationRequest;
import com.nexus.platform.entity.CollaborationStatus;
import com.nexus.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollaborationRequestRepository extends JpaRepository<CollaborationRequest, Long> {
    List<CollaborationRequest> findByInvestorId(Long investorId);
    List<CollaborationRequest> findByEntrepreneurId(Long entrepreneurId);
    List<CollaborationRequest> findByEntrepreneurAndStatus(User entrepreneur, CollaborationStatus status);
    List<CollaborationRequest> findByInvestorAndStatus(User investor, CollaborationStatus status);
}
