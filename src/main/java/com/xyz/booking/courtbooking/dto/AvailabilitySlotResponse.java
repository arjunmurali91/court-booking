package com.xyz.booking.courtbooking.dto;

public class AvailabilitySlotResponse {

	private String hour;
	private boolean available;

	public AvailabilitySlotResponse(String hour, boolean available) {
		this.hour = hour;
		this.available = available;
	}

	public String getHour() {
		return hour;
	}

	public boolean isAvailable() {
		return available;
	}
}
