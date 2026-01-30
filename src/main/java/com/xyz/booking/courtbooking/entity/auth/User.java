package com.xyz.booking.courtbooking.entity.auth;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "appuser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordhash;

    @Column(nullable = false)
    private String authprovider;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private OffsetDateTime createdat;

    @Column(nullable = false)
    private OffsetDateTime updatedat;

    private OffsetDateTime lastloginat;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserDetails userdetails;

    protected User() {}

    @PrePersist
    void onCreate() {
        createdat = OffsetDateTime.now();
        updatedat = createdat;
    }

    @PreUpdate
    void onUpdate() {
        updatedat = OffsetDateTime.now();
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordhash() { return passwordhash; }
    public void setPasswordhash(String passwordhash) { this.passwordhash = passwordhash; }

    public String getAuthprovider() { return authprovider; }
    public void setAuthprovider(String authprovider) { this.authprovider = authprovider; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public OffsetDateTime getLastloginat() { return lastloginat; }
    public void setLastloginat(OffsetDateTime lastloginat) { this.lastloginat = lastloginat; }

    public UserDetails getUserdetails() { return userdetails; }
    public void setUserdetails(UserDetails userdetails) { this.userdetails = userdetails; }
}
