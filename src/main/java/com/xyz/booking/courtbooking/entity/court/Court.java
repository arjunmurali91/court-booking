package com.xyz.booking.courtbooking.entity.court;

import java.time.Instant;

import com.xyz.booking.courtbooking.config.enums.SportType;
import com.xyz.booking.courtbooking.entity.facility.Facility;

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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "court",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"facilityid", "name"})
    },
    indexes = {
        @Index(name = "idx_court_facility", columnList = "facilityid")
    }
)
public class Court {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "facilityid", nullable = false)
    private Facility facility;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private SportType sport;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false, updatable = false)
    private Instant createdat;

    @Column(nullable = false)
    private Instant updatedat;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdat = now;
        this.updatedat = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedat = Instant.now();
    }

    // Getters

    public Long getId() {
        return id;
    }

    public Facility getFacility() {
        return facility;
    }

    public String getName() {
        return name;
    }

    public SportType getSport() {
        return sport;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedat() {
        return createdat;
    }

    public Instant getUpdatedat() {
        return updatedat;
    }


    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSport(SportType sport) {
        this.sport = sport;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
