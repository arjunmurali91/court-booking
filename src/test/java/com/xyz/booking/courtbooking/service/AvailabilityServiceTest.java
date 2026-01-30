package com.xyz.booking.courtbooking.service;

import com.xyz.booking.courtbooking.config.enums.BookingStatus;
import com.xyz.booking.courtbooking.dto.AvailabilitySlotResponse;
import com.xyz.booking.courtbooking.entity.booking.Booking;
import com.xyz.booking.courtbooking.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvailabilityServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private AvailabilityService availabilityService;

    @Test
    void shouldReturnAllSlotsAvailableWhenNoBookingsExist() {
        when(bookingRepository.findActiveBookingsForDay(
                eq(1L),
                any(Instant.class),
                any(Instant.class),
                any(Instant.class),
                eq(BookingStatus.CONFIRMED),
                eq(BookingStatus.HELD)
        )).thenReturn(List.of());

        List<AvailabilitySlotResponse> result =
                availabilityService.getAvailability(1L, LocalDate.now());

        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(AvailabilitySlotResponse::isAvailable);
    }

    @Test
    void shouldMarkSlotUnavailableWhenBookingExistsForThatHour() {
        Booking booking = new Booking();
        booking.setStarttime(
                LocalDate.now()
                        .atTime(10, 0)
                        .toInstant(ZoneOffset.UTC)
        );

        when(bookingRepository.findActiveBookingsForDay(
                eq(1L),
                any(Instant.class),
                any(Instant.class),
                any(Instant.class),
                eq(BookingStatus.CONFIRMED),
                eq(BookingStatus.HELD)
        )).thenReturn(List.of(booking));

        List<AvailabilitySlotResponse> result =
                availabilityService.getAvailability(1L, LocalDate.now());

        AvailabilitySlotResponse tenAmSlot = result.stream()
                .filter(s -> s.getHour().equals("10:00"))
                .findFirst()
                .orElseThrow();

        assertThat(tenAmSlot.isAvailable()).isFalse();
    }
}
