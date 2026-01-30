package com.xyz.booking.courtbooking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.booking.courtbooking.dto.CourtResponse;
import com.xyz.booking.courtbooking.dto.CreateCourtRequest;
import com.xyz.booking.courtbooking.entity.court.Court;
import com.xyz.booking.courtbooking.service.CourtService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/courts")
@Tag(name = "Court")
public class CourtController {

    private final CourtService service;

    public CourtController(CourtService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Court> create(@RequestBody CreateCourtRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<CourtResponse>> getByFacility(
            @RequestParam Long facilityId) {
        return ResponseEntity.ok(service.getByFacility(facilityId));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Court> update(
            @PathVariable Long id,
            @RequestBody CreateCourtRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
