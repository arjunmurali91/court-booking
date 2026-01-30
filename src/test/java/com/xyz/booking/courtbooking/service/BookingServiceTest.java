package com.xyz.booking.courtbooking.service;

import com.xyz.booking.courtbooking.config.enums.BookingStatus;
import com.xyz.booking.courtbooking.entity.booking.Booking;
import com.xyz.booking.courtbooking.entity.court.Court;
import com.xyz.booking.courtbooking.exception.BookingConflictException;
import com.xyz.booking.courtbooking.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

        @Mock
        private BookingRepository bookingRepository;

        @Mock
        private CourtService courtService;

        private BookingService bookingService;

        @BeforeEach
        void setup() {
                bookingService = new BookingService(
                                bookingRepository,
                                courtService,
                                300L // holdDurationSeconds = 5 minutes
                );
        }

        @Test
        void shouldCreateHoldWhenSlotIsAvailable() {
                Instant start = Instant.now().plusSeconds(3600);

                Court court = new Court();
                ReflectionTestUtils.setField(court, "id", 1L);

                when(bookingRepository.existsOverlappingBooking(
                                eq(1L),
                                eq(start),
                                any(Instant.class),
                                any(Instant.class),
                                eq(BookingStatus.CONFIRMED),
                                eq(BookingStatus.HELD))).thenReturn(false);

                when(courtService.getByIdWithLock(1L)).thenReturn(court);
                when(bookingRepository.save(any(Booking.class)))
                                .thenAnswer(invocation -> invocation.getArgument(0));

                Booking booking = bookingService.createHold(1L, 100L, start);

                assertThat(booking.getStatus()).isEqualTo(BookingStatus.HELD);
                assertThat(booking.getHoldexpiresat()).isNotNull();
                assertThat(booking.getCourt()).isEqualTo(court);
        }

        @Test
        void shouldRejectHoldWhenSlotAlreadyBooked() {
                Instant start = Instant.now().plusSeconds(3600);

                when(courtService.getByIdWithLock(1L)).thenReturn(new Court());

                when(bookingRepository.existsOverlappingBooking(
                                anyLong(),
                                any(),
                                any(),
                                any(),
                                eq(BookingStatus.CONFIRMED),
                                eq(BookingStatus.HELD))).thenReturn(true);

                assertThatThrownBy(() -> bookingService.createHold(1L, 200L, start))
                                .isInstanceOf(BookingConflictException.class)
                                .hasMessage("Slot already booked");
        }

        @Test
        void shouldConfirmValidHold() {
                Booking booking = new Booking();
                booking.setStatus(BookingStatus.HELD);
                booking.setHoldexpiresat(Instant.now().plusSeconds(300));

                UUID transactionId = UUID.randomUUID();

                when(bookingRepository.findByTransactionid(transactionId))
                                .thenReturn(Optional.of(booking));
                when(bookingRepository.save(any(Booking.class)))
                                .thenAnswer(invocation -> invocation.getArgument(0));

                Booking confirmed = bookingService.confirm(transactionId);

                assertThat(confirmed.getStatus()).isEqualTo(BookingStatus.CONFIRMED);
                assertThat(confirmed.getHoldexpiresat()).isNull();
        }

        @Test
        void shouldRejectConfirmWhenHoldExpired() {
                Booking booking = new Booking();
                booking.setStatus(BookingStatus.HELD);
                booking.setHoldexpiresat(Instant.now().minusSeconds(10));

                UUID transactionId = UUID.randomUUID();

                when(bookingRepository.findByTransactionid(transactionId))
                                .thenReturn(Optional.of(booking));

                assertThatThrownBy(() -> bookingService.confirm(transactionId))
                                .isInstanceOf(IllegalStateException.class)
                                .hasMessage("Hold expired");
        }

        @Test
        void shouldCancelConfirmedBooking() {
                Booking booking = new Booking();
                booking.setStatus(BookingStatus.CONFIRMED);
                booking.setHoldexpiresat(Instant.now().plusSeconds(300));

                UUID transactionId = UUID.randomUUID();

                when(bookingRepository.findByTransactionid(transactionId))
                                .thenReturn(Optional.of(booking));
                when(bookingRepository.save(any(Booking.class)))
                                .thenAnswer(invocation -> invocation.getArgument(0));

                Booking cancelled = bookingService.cancel(transactionId);

                assertThat(cancelled.getStatus()).isEqualTo(BookingStatus.CANCELLED);
                assertThat(cancelled.getHoldexpiresat()).isNull();
        }

        @Test
        void shouldReturnBookingIfAlreadyCancelled() {
                Booking booking = new Booking();
                booking.setStatus(BookingStatus.CANCELLED);

                UUID transactionId = UUID.randomUUID();

                when(bookingRepository.findByTransactionid(transactionId))
                                .thenReturn(Optional.of(booking));

                Booking result = bookingService.cancel(transactionId);

                assertThat(result.getStatus()).isEqualTo(BookingStatus.CANCELLED);
                verify(bookingRepository, never()).save(any());
        }

        @Test
        void shouldRejectCancelIfNotConfirmed() {
                Booking booking = new Booking();
                booking.setStatus(BookingStatus.HELD);

                UUID transactionId = UUID.randomUUID();

                when(bookingRepository.findByTransactionid(transactionId))
                                .thenReturn(Optional.of(booking));

                assertThatThrownBy(() -> bookingService.cancel(transactionId)).isInstanceOf(IllegalStateException.class)
                                .hasMessage("Only confirmed bookings can be cancelled");
        }
}
