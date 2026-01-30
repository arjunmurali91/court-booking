package com.xyz.booking.courtbooking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xyz.booking.courtbooking.dto.CourtResponse;
import com.xyz.booking.courtbooking.dto.CreateCourtRequest;
import com.xyz.booking.courtbooking.entity.court.Court;
import com.xyz.booking.courtbooking.entity.facility.Facility;
import com.xyz.booking.courtbooking.repository.CourtRepository;

@Service
public class CourtService {

	private final CourtRepository courtRepository;
	private final FacilityService facilityService;

	public CourtService(CourtRepository courtRepository, FacilityService facilityService) {
		this.courtRepository = courtRepository;
		this.facilityService = facilityService;
	}

	public Court create(CreateCourtRequest request) {
		Facility facility = facilityService.findById(request.getFacilityId());

		Court court = new Court();
		court.setFacility(facility);
		court.setName(request.getName());
		court.setSport(request.getSport());

		return courtRepository.save(court);
	}

	public List<CourtResponse> getByFacility(Long facilityId) {
		return courtRepository.findByFacilityId(facilityId).stream().map(
				c -> new CourtResponse(c.getId(), c.getFacility().getId(), c.getName(), c.getSport(), c.isActive()))
				.toList();
	}

	public Court getById(Long id) {
		return courtRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Court not found"));
	}

	public Court getByIdWithLock(Long id) {
		return courtRepository.findByIdWithLock(id)
				.orElseThrow(() -> new IllegalArgumentException("Court not found"));
	}

	public Court update(Long id, CreateCourtRequest request) {
		Court court = getById(id);
		court.setName(request.getName());
		court.setSport(request.getSport());
		return courtRepository.save(court);
	}

	public void deactivate(Long id) {
		Court court = getById(id);
		court.setActive(false);
		courtRepository.save(court);
	}
}
