package com.xyz.booking.courtbooking.service;

import com.xyz.booking.courtbooking.entity.facility.Facility;
import com.xyz.booking.courtbooking.repository.FacilityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacilityServiceTest {

    @Mock
    private FacilityRepository facilityRepository;

    @InjectMocks
    private FacilityService facilityService;

    @Test
    void shouldReturnAllFacilitiesWhenLocationIsNull() {
        Facility facility = new Facility();
        facility.setName("Arena 1");
        facility.setLocation("Bangalore");
        facility.setActive(true);

        when(facilityRepository.findAll()).thenReturn(List.of(facility));

        var result = facilityService.getFacilities(null);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Arena 1");
    }

    @Test
    void shouldFilterFacilitiesByLocation() {
        Facility facility = new Facility();
        facility.setName("Arena BLR");
        facility.setLocation("Bangalore");
        facility.setActive(true);

        when(facilityRepository.findByLocationIgnoreCase("Bangalore"))
                .thenReturn(List.of(facility));

        var result = facilityService.getFacilities("Bangalore");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getLocation()).isEqualTo("Bangalore");
    }

    @Test
    void shouldReturnEmptyListWhenNoFacilitiesMatch() {
        when(facilityRepository.findByLocationIgnoreCase("Chennai"))
                .thenReturn(List.of());

        var result = facilityService.getFacilities("Chennai");

        assertThat(result).isEmpty();
    }
}
