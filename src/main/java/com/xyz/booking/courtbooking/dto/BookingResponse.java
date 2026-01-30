package com.xyz.booking.courtbooking.dto;

import java.time.Instant;
import java.util.UUID;

import com.xyz.booking.courtbooking.config.enums.BookingStatus;

public class BookingResponse {

	private UUID transactionId;
	private BookingStatus status;
	private Instant startTime;
	private Instant endTime;

	public BookingResponse(UUID transactionId, BookingStatus status, Instant startTime, Instant endTime) {
		this.transactionId = transactionId;
		this.status = status;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public UUID getTransactionId() {
		return transactionId;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public Instant getStartTime() {
		return startTime;
	}

	public Instant getEndTime() {
		return endTime;
	}
}
