package com.xyz.booking.courtbooking.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.xyz.booking.courtbooking.config.enums.BookingStatus;
import com.xyz.booking.courtbooking.dto.AvailabilitySlotResponse;
import com.xyz.booking.courtbooking.entity.booking.Booking;
import com.xyz.booking.courtbooking.repository.BookingRepository;

@Service
public class AvailabilityService {

    private final BookingRepository bookingRepository;

    public AvailabilityService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<AvailabilitySlotResponse> getAvailability(
            Long courtId,
            LocalDate date
    ) {
        Instant dayStart = date.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant dayEnd = dayStart.plus(1, ChronoUnit.DAYS);
        Instant now = Instant.now();

        List<Booking> bookings =
                bookingRepository.findActiveBookingsForDay(
                        courtId,
                        dayStart,
                        dayEnd,
                        now,
                        BookingStatus.CONFIRMED,
                        BookingStatus.HELD
                );

        Set<Integer> blockedHours = bookings.stream()
                .map(b -> b.getStarttime()
                        .atZone(ZoneOffset.UTC)
                        .getHour())
                .collect(Collectors.toSet());

        List<AvailabilitySlotResponse> response = new ArrayList<>();

        // Example: 6 AM – 10 PM
        for (int hour = 6; hour < 22; hour++) {
            response.add(
                new AvailabilitySlotResponse(
                    String.format("%02d:00", hour),
                    !blockedHours.contains(hour)
                )
            );
        }

        return response;
    }
}