package com.xyz.booking.courtbooking.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xyz.booking.courtbooking.entity.facility.Facility;

public interface FacilityRepository extends JpaRepository<Facility, Long> {

    List<Facility> findByLocationIgnoreCase(String location);

    List<Facility> findByLocationIgnoreCaseAndActiveTrue(String location);
}

