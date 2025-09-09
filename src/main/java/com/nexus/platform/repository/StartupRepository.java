package com.nexus.platform.repository;

import com.nexus.platform.entity.Startup;
import com.nexus.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StartupRepository extends JpaRepository<Startup, Long>, JpaSpecificationExecutor<Startup> {
    Optional<Startup> findByUser(User user);

    @Query("SELECT COUNT(DISTINCT s.industry) FROM Startup s")
    long countDistinctIndustry();
}
