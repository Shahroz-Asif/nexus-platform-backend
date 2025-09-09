package com.nexus.platform.repository;

import com.nexus.platform.entity.InvestorProfile;
import com.nexus.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvestorProfileRepository extends JpaRepository<InvestorProfile, Long>, JpaSpecificationExecutor<InvestorProfile> {
    Optional<InvestorProfile> findByUser(User user);
}
