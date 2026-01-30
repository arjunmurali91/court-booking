package com.xyz.booking.courtbooking.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.booking.courtbooking.dto.BookingCheckinResponse;
import com.xyz.booking.courtbooking.dto.BookingResponse;
import com.xyz.booking.courtbooking.dto.CreateHoldRequest;
import com.xyz.booking.courtbooking.entity.booking.Booking;
import com.xyz.booking.courtbooking.service.BookingService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/bookings")
@Tag(name = "Booking")
public class BookingController {

    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @PostMapping("/hold")
    public ResponseEntity<BookingResponse> hold(@RequestBody CreateHoldRequest req) {
        Booking booking = service.createHold(
                req.getCourtId(),
                req.getUserId(),
                req.getStartTime()
        );

        return ResponseEntity.ok(toResponse(booking));
    }

    @PostMapping("/{transactionId}/confirm")
    public ResponseEntity<BookingResponse> confirm(@PathVariable UUID transactionId) {
        Booking booking = service.confirm(transactionId);
        return ResponseEntity.ok(toResponse(booking));
    }

    private BookingResponse toResponse(Booking booking) {
        return new BookingResponse(
                booking.getTransactionid(),
                booking.getStatus(),
                booking.getStarttime(),
                booking.getEndtime()
        );
    }
    
    @PostMapping("/{transactionId}/cancel")
    public ResponseEntity<BookingResponse> cancel(@PathVariable UUID transactionId) {
        Booking booking = service.cancel(transactionId);
        return ResponseEntity.ok(toResponse(booking));
    }
    
    @PostMapping("/{transactionId}/checkin")
    public ResponseEntity<BookingCheckinResponse> checkIn(
            @PathVariable UUID transactionId
    ) {
        Booking booking = service.checkIn(transactionId);

        return ResponseEntity.ok(
            new BookingCheckinResponse(
                booking.getTransactionid(),
                booking.getStatus(),
                booking.getCourt().getId(),
                booking.getStarttime(),
                booking.getEndtime(),
                booking.getCheckedinat()
            )
        );
    }


}
