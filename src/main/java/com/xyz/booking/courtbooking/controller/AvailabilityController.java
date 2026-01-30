package com.xyz.booking.courtbooking.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.booking.courtbooking.dto.AvailabilitySlotResponse;
import com.xyz.booking.courtbooking.service.AvailabilityService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/courts")
@Tag(name = "Availability")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping("/{courtId}/availability")
    public List<AvailabilitySlotResponse> availability(
            @PathVariable Long courtId,
            @RequestParam String date
    ) {
        return availabilityService.getAvailability(
                courtId,
                LocalDate.parse(date)
        );
    }
}
