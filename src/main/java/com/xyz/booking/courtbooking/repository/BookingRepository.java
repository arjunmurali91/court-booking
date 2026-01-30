package com.xyz.booking.courtbooking.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xyz.booking.courtbooking.config.enums.BookingStatus;
import com.xyz.booking.courtbooking.entity.booking.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	@Query("""
			    select count(b) > 0
			    from Booking b
			    where b.court.id = :courtId
			      and (
			            b.status = :confirmed
			            or (b.status = :held and b.holdexpiresat > :now)
			          )
			      and b.starttime < :endTime
			      and b.endtime > :startTime
			""")
	boolean existsOverlappingBooking(@Param("courtId") Long courtId, @Param("startTime") Instant startTime,
			@Param("endTime") Instant endTime, @Param("now") Instant now, @Param("confirmed") BookingStatus confirmed,
			@Param("held") BookingStatus held);

	Optional<Booking> findByTransactionid(UUID transactionid);

	@Query("""
			    select b
			    from Booking b
			    where b.court.id = :courtId
			      and (
			            b.status = :confirmed
			            or (b.status = :held and b.holdexpiresat > :now)
			          )
			      and b.starttime < :dayEnd
			      and b.endtime > :dayStart
			""")
	List<Booking> findActiveBookingsForDay(@Param("courtId") Long courtId, @Param("dayStart") Instant dayStart,
			@Param("dayEnd") Instant dayEnd, @Param("now") Instant now, @Param("confirmed") BookingStatus confirmed,
			@Param("held") BookingStatus held);


}
