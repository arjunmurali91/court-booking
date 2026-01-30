package com.xyz.booking.courtbooking.entity.auth;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "refreshtoken")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    @Column(nullable = false)
    private String tokenhash;

    @Column(nullable = false)
    private OffsetDateTime issuedat;

    @Column(nullable = false)
    private OffsetDateTime expiresat;

    private OffsetDateTime revokedat;
    private String deviceinfo;
    private String ipaddress;

    protected RefreshToken() {}

    @PrePersist
    void onCreate() {
        issuedat = OffsetDateTime.now();
    }

    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getTokenhash() { return tokenhash; }
    public void setTokenhash(String tokenhash) { this.tokenhash = tokenhash; }

    public OffsetDateTime getExpiresat() { return expiresat; }
    public void setExpiresat(OffsetDateTime expiresat) { this.expiresat = expiresat; }

    public OffsetDateTime getRevokedat() { return revokedat; }
    public void setRevokedat(OffsetDateTime revokedat) { this.revokedat = revokedat; }
}
