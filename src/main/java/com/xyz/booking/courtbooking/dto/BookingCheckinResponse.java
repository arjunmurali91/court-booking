package com.xyz.booking.courtbooking.dto;

import java.time.Instant;
import java.util.UUID;

import com.xyz.booking.courtbooking.config.enums.BookingStatus;

public class BookingCheckinResponse {

    private UUID transactionId;
    private BookingStatus status;
    private Long courtId;
    private Instant startTime;
    private Instant endTime;
    private Instant checkedInAt;

    public BookingCheckinResponse(
            UUID transactionId,
            BookingStatus status,
            Long courtId,
            Instant startTime,
            Instant endTime,
            Instant checkedInAt
    ) {
        this.transactionId = transactionId;
        this.status = status;
        this.courtId = courtId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.checkedInAt = checkedInAt;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public Long getCourtId() {
        return courtId;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public Instant getCheckedInAt() {
        return checkedInAt;
    }
}
