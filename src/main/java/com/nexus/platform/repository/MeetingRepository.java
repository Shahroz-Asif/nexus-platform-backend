package com.nexus.platform.repository;

import com.nexus.platform.entity.Meeting;
import com.nexus.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    @Query("SELECT m FROM Meeting m JOIN m.participants p WHERE p IN :participants AND m.status = com.nexus.platform.entity.MeetingStatus.SCHEDULED AND m.dateTime < :endTime AND m.endTime > :startTime")
    List<Meeting> findOverlappingMeetings(@Param("participants") Set<User> participants, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT m FROM Meeting m JOIN m.participants p WHERE p IN :participants AND m.id <> :meetingId AND m.status = com.nexus.platform.entity.MeetingStatus.SCHEDULED AND m.dateTime < :endTime AND m.endTime > :startTime")
    List<Meeting> findOverlappingMeetingsForUpdate(@Param("participants") Set<User> participants, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime, @Param("meetingId") Long meetingId);

    List<Meeting> findByParticipantsContainsAndDateTimeAfter(User user, LocalDateTime dateTime);
}
