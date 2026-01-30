package com.xyz.booking.courtbooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xyz.booking.courtbooking.entity.court.Court;

import jakarta.persistence.LockModeType;

public interface CourtRepository extends JpaRepository<Court, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Court c WHERE c.id = :id")
    Optional<Court> findByIdWithLock(@Param("id") Long id);

    List<Court> findByFacilityId(Long facilityId);
}
