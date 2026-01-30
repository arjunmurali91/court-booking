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

import com.xyz.booking.courtbooking.dto.FacilityResponse;
import com.xyz.booking.courtbooking.entity.facility.Facility;
import com.xyz.booking.courtbooking.service.FacilityService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/facilities")
@Tag(name = "Facility")
public class FacilityController {

    private final FacilityService service;

    public FacilityController(FacilityService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Facility> create(@RequestBody Facility facility) {
        return ResponseEntity.ok(service.create(facility));
    }

    @GetMapping
    public ResponseEntity<List<Facility>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Facility> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Facility> update(
            @PathVariable Long id,
            @RequestBody Facility facility) {
        return ResponseEntity.ok(service.update(id, facility));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/facilities-by-location")
    public List<FacilityResponse> getFacilities(
            @RequestParam(required = false) String location
    ) {
        return service.getFacilities(location);
    }

}
