package com.xyz.booking.courtbooking.dto;


import com.xyz.booking.courtbooking.config.enums.SportType;

import lombok.Getter;

@Getter
public class CourtResponse {

    private Long id;
    private Long facilityId;
    private String name;
    private SportType sport;
    private boolean active;

    public CourtResponse(Long id, Long facilityId, String name, SportType sport, boolean active) {
        this.id = id;
        this.facilityId = facilityId;
        this.name = name;
        this.sport = sport;
        this.active = active;
    }

}
