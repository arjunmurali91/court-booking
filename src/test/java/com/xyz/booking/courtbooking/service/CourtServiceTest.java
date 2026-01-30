package com.xyz.booking.courtbooking.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.xyz.booking.courtbooking.config.enums.SportType;
import com.xyz.booking.courtbooking.dto.CourtResponse;
import com.xyz.booking.courtbooking.dto.CreateCourtRequest;
import com.xyz.booking.courtbooking.entity.court.Court;
import com.xyz.booking.courtbooking.entity.facility.Facility;
import com.xyz.booking.courtbooking.repository.CourtRepository;

@ExtendWith(MockitoExtension.class)
class CourtServiceTest {

    @Mock
    private CourtRepository courtRepository;

    @Mock
    private FacilityService facilityService;

    @InjectMocks
    private CourtService courtService;

    @Test
    void shouldCreateCourtSuccessfully() {
        Facility facility = new Facility();
        ReflectionTestUtils.setField(facility, "id", 1L);

        CreateCourtRequest request = new CreateCourtRequest();
        ReflectionTestUtils.setField(request, "facilityId", 1L);
        ReflectionTestUtils.setField(request, "name", "Court 1");
        ReflectionTestUtils.setField(request, "sport", SportType.BADMINTON);

        when(facilityService.findById(1L)).thenReturn(facility);
        when(courtRepository.save(any(Court.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Court result = courtService.create(request);

        assertThat(result.getName()).isEqualTo("Court 1");
        assertThat(result.getSport()).isEqualTo(SportType.BADMINTON);
        assertThat(result.getFacility().getId()).isEqualTo(1L);
        assertThat(result.isActive()).isTrue();
    }

    @Test
    void shouldReturnCourtsByFacility() {
        Facility facility = new Facility();
        ReflectionTestUtils.setField(facility, "id", 1L);

        Court court = new Court();
        ReflectionTestUtils.setField(court, "id", 10L);
        court.setFacility(facility);
        court.setName("Court A");
        court.setSport(SportType.TENNIS);
        court.setActive(true);

        when(courtRepository.findByFacilityId(1L))
                .thenReturn(List.of(court));

        List<CourtResponse> result = courtService.getByFacility(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(10L);
        assertThat(result.get(0).getFacilityId()).isEqualTo(1L);
        assertThat(result.get(0).getName()).isEqualTo("Court A");
        assertThat(result.get(0).getSport()).isEqualTo(SportType.TENNIS);
        assertThat(result.get(0).isActive()).isTrue();
    }

    @Test
    void shouldReturnCourtById() {
        Court court = new Court();
        ReflectionTestUtils.setField(court, "id", 5L);

        when(courtRepository.findById(5L))
                .thenReturn(Optional.of(court));

        Court result = courtService.getById(5L);

        assertThat(result.getId()).isEqualTo(5L);
    }

    @Test
    void shouldThrowExceptionWhenCourtNotFound() {
        when(courtRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> courtService.getById(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Court not found");
    }

    @Test
    void shouldUpdateCourt() {
        Court court = new Court();
        ReflectionTestUtils.setField(court, "id", 3L);
        court.setName("Old Name");
        court.setSport(SportType.FOOTBALL);

        CreateCourtRequest request = new CreateCourtRequest();
        ReflectionTestUtils.setField(request, "name", "New Name");
        ReflectionTestUtils.setField(request, "sport", SportType.SQUASH);

        when(courtRepository.findById(3L))
                .thenReturn(Optional.of(court));
        when(courtRepository.save(any(Court.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Court updated = courtService.update(3L, request);

        assertThat(updated.getName()).isEqualTo("New Name");
        assertThat(updated.getSport()).isEqualTo(SportType.SQUASH);
    }

    @Test
    void shouldDeactivateCourt() {
        Court court = new Court();
        ReflectionTestUtils.setField(court, "id", 7L);
        court.setActive(true);

        when(courtRepository.findById(7L))
                .thenReturn(Optional.of(court));

        courtService.deactivate(7L);

        assertThat(court.isActive()).isFalse();
        verify(courtRepository).save(court);
    }
}
