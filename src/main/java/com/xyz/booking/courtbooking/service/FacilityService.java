package com.xyz.booking.courtbooking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xyz.booking.courtbooking.dto.FacilityResponse;
import com.xyz.booking.courtbooking.entity.facility.Facility;
import com.xyz.booking.courtbooking.repository.FacilityRepository;

@Service
public class FacilityService {

	private final FacilityRepository repository;

	public FacilityService(FacilityRepository repository) {
		this.repository = repository;
	}

	public Facility create(Facility facility) {
		return repository.save(facility);
	}

	public List<Facility> findAll() {
		return repository.findAll();
	}

	public Facility findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Facility not found"));
	}

	public Facility update(Long id, Facility updated) {
		Facility existing = findById(id);
		existing.setName(updated.getName());
		existing.setLocation(updated.getLocation());
		return repository.save(existing);
	}

	public void deactivate(Long id) {
		Facility facility = findById(id);
		facility.setActive(false);
		repository.save(facility);
	}

	public List<FacilityResponse> getFacilities(String location) {

	    List<Facility> facilities;

	    if (location != null && !location.isBlank()) {
	        facilities = repository.findByLocationIgnoreCase(location);
	    } else {
	        facilities = repository.findAll();
	    }

	    return facilities.stream()
	            .map(this::toResponse)
	            .toList();
	}


	private FacilityResponse toResponse(Facility facility) {
		return new FacilityResponse(facility.getId(), facility.getName(), facility.getLocation(), facility.isActive());
	}

}
