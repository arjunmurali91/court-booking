package com.xyz.booking.courtbooking.entity.facility;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.xyz.booking.courtbooking.entity.court.Court;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "facility")
public class Facility {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 150)
	private String name;

	@Column(nullable = false, length = 255)
	private String location;

	@Column(nullable = false)
	private boolean active = true;

	@Column(nullable = false, updatable = false)
	private Instant createdat;

	@Column(nullable = false)
	private Instant updatedat;

	@OneToMany(mappedBy = "facility", fetch = FetchType.LAZY)
	private List<Court> courts = new ArrayList<>();

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

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
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
}
