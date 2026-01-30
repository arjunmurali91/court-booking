package com.xyz.booking.courtbooking.dto;

import com.xyz.booking.courtbooking.config.enums.SportType;

public class CreateCourtRequest {

	private Long facilityId;
	private String name;
	private SportType sport;

	public Long getFacilityId() {
		return facilityId;
	}

	public String getName() {
		return name;
	}

	public SportType getSport() {
		return sport;
	}
}
