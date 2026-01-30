package com.xyz.booking.courtbooking.dto;


import java.time.Instant;

public class CreateHoldRequest {
    private Long courtId;
    private Long userId;
    private Instant startTime;

    public Long getCourtId() { return courtId; }
    public Long getUserId() { return userId; }
    public Instant getStartTime() { return startTime; }
}
