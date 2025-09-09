package com.nexus.platform.repository;

import com.nexus.platform.entity.ProfileView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileViewRepository extends JpaRepository<ProfileView, Long> {
    int countByViewedProfileId(Long viewedProfileId);
}
