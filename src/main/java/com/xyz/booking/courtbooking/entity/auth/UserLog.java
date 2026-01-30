package com.xyz.booking.courtbooking.entity.auth;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "userlog")
public class UserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    @Column(nullable = false)
    private String eventtype;

    @Column(nullable = false)
    private OffsetDateTime eventtime;

    private String ipaddress;
    private String useragent;

    @Column(columnDefinition = "jsonb")
    private String metadata;

    protected UserLog() {}

    @PrePersist
    void onCreate() {
        eventtime = OffsetDateTime.now();
    }

    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getEventtype() { return eventtype; }
    public void setEventtype(String eventtype) { this.eventtype = eventtype; }

    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }
}
