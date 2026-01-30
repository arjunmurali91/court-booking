# Court Booking Service

Backend service for a sports facility / court booking platform.
The system focuses on **concurrency-safe booking**, **derived availability**, and **time-based pricing**, rather than UI or payment processing.

---

## Features

- Facility and court management
- Derived court availability (no slot table)
- Fixed 1-hour booking slots
- Hold-based booking to prevent double booking
- Strong consistency for booking operations
- Cancellation, expiry, and no-show handling
- REST APIs with Swagger documentation
- Unit test coverage for core business logic

---

##  High-Level Architecture

- **Controllers**: REST API layer
- **Services**:
  - FacilityService
  - CourtService
  - AvailabilityService
  - BookingService
- **Persistence**: PostgreSQL via Spring Data JPA
- **Background Jobs**: Cron-based cleanup for expired holds and no-shows

---

##  Key Design Decisions

- Availability is derived dynamically from bookings
- Booking uses a temporary **HOLD → CONFIRMED** lifecycle
- Strong consistency enforced during booking creation
- Pricing is informational and layered into availability
- Time-based state transitions handled outside request flow

---


---

##  Assumptions

- No facility-level schedules or downtime
- Courts are the atomic booking unit
- Booking slots are fixed to 1 hour
- Payments and refunds are out of scope

---

##  Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Swagger / OpenAPI
- JUnit + Mockito

---


```bash
mvn spring-boot:run
```

Swagger UI:
```
http://localhost:8080/swagger-ui/index.html
```

---

##  Tests

Run unit tests with:

```bash
mvn clean test
```

Coverage is focused on service-layer business logic.

---

##  Background Jobs

Expired holds and no-shows are handled via cron-based SQL scripts to keep APIs synchronous and simple.

---

##  Notes

This project is intentionally scoped to demonstrate:
- Backend design
- Concurrency handling
