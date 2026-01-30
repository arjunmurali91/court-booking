package com.xyz.booking.courtbooking.entity.booking;

import java.time.Instant;
import java.util.UUID;

import com.xyz.booking.courtbooking.config.enums.BookingStatus;
import com.xyz.booking.courtbooking.entity.court.Court;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "booking", indexes = { @Index(name = "idx_booking_court_time", columnList = "courtid,starttime,endtime"),
		@Index(name = "idx_booking_transaction", columnList = "transactionid", unique = true) })
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, updatable = false)
	private UUID transactionid;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "courtid", nullable = false)
	private Court court;

	@Column(nullable = false)
	private Long userid; // stub for now

	@Column(nullable = false)
	private Instant starttime;

	@Column(nullable = false)
	private Instant endtime;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private BookingStatus status;

	@Column
	private Instant holdexpiresat;

	@Column(nullable = false, updatable = false)
	private Instant createdat;
	
	@Column
	private Instant checkedinat;


	@PrePersist
	protected void onCreate() {
		this.createdat = Instant.now();
		this.transactionid = UUID.randomUUID();
	}

}
