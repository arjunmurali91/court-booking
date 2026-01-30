package com.xyz.booking.courtbooking.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xyz.booking.courtbooking.config.enums.BookingStatus;
import com.xyz.booking.courtbooking.entity.booking.Booking;
import com.xyz.booking.courtbooking.entity.court.Court;
import com.xyz.booking.courtbooking.exception.BookingConflictException;
import com.xyz.booking.courtbooking.repository.BookingRepository;

@Service
public class BookingService {

	private final BookingRepository bookingRepository;
	private final CourtService courtService;
	private final long holdDurationSeconds;

	public BookingService(BookingRepository bookingRepository, CourtService courtService,
			@Value("${booking.hold.duration.seconds}") long holdDurationSeconds) {
		this.bookingRepository = bookingRepository;
		this.courtService = courtService;
		this.holdDurationSeconds = holdDurationSeconds;
	}

	@Transactional
	public Booking createHold(Long courtId, Long userId, Instant startTime) {
		// LOCK FIRST to serialize access to this court
		Court court = courtService.getByIdWithLock(courtId);

		Instant endTime = startTime.plus(1, ChronoUnit.HOURS);
		Instant now = Instant.now();

		boolean conflict = bookingRepository.existsOverlappingBooking(courtId, startTime, endTime, now,
				BookingStatus.CONFIRMED, BookingStatus.HELD);

		if (conflict) {
			throw new BookingConflictException("Slot already booked");
		}

		Booking booking = new Booking();
		booking.setCourt(court);
		booking.setUserid(userId);
		booking.setStarttime(startTime);
		booking.setEndtime(endTime);
		booking.setStatus(BookingStatus.HELD);
		booking.setHoldexpiresat(now.plusSeconds(holdDurationSeconds));

		return bookingRepository.save(booking);
	}

	@Transactional
	public Booking confirm(UUID transactionId) {
		Booking booking = bookingRepository.findByTransactionid(transactionId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid transaction"));

		if (booking.getStatus() != BookingStatus.HELD || booking.getHoldexpiresat().isBefore(Instant.now())) {
			throw new IllegalStateException("Hold expired");
		}

		booking.setStatus(BookingStatus.CONFIRMED);
		booking.setHoldexpiresat(null);

		return bookingRepository.save(booking);
	}

	@Transactional
	public Booking cancel(UUID transactionId) {

		Booking booking = bookingRepository.findByTransactionid(transactionId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid transaction"));

		if (booking.getStatus() == BookingStatus.CANCELLED) {
			return booking;
		}

		if (booking.getStatus() != BookingStatus.CONFIRMED) {
			throw new IllegalStateException("Only confirmed bookings can be cancelled");
		}

		booking.setStatus(BookingStatus.CANCELLED);
		booking.setHoldexpiresat(null);

		return bookingRepository.save(booking);
	}

	@Transactional
	public Booking checkIn(UUID transactionId) {

		Booking booking = bookingRepository.findByTransactionid(transactionId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid transaction"));

		if (booking.getStatus() != BookingStatus.CONFIRMED) {
			throw new IllegalStateException("Only confirmed bookings can be checked in");
		}

		booking.setStatus(BookingStatus.COMPLETED);
		booking.setCheckedinat(Instant.now());

		return bookingRepository.save(booking);
	}

}
