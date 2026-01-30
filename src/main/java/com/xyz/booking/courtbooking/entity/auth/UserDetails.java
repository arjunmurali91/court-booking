package com.xyz.booking.courtbooking.entity.auth;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "userdetails")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "userid", nullable = false, unique = true)
    private User user;

    private String firstname;
    private String lastname;
    private String phone;
    private LocalDate dateofbirth;
    private String timezone;

    protected UserDetails() {}

    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDate getDateofbirth() { return dateofbirth; }
    public void setDateofbirth(LocalDate dateofbirth) { this.dateofbirth = dateofbirth; }

    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }
}
