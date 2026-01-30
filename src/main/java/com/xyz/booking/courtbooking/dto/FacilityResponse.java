package com.xyz.booking.courtbooking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacilityResponse {

    private Long id;
    private String name;
    private String location;
    private boolean active;

    public FacilityResponse(Long id, String name, String location, boolean active) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.active = active;
    }

}
