package com.daniespadel.app.booking;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "bookings",
  indexes = {
    @Index(name="idx_booking_user", columnList="userId"),
    @Index(name="idx_booking_court_start_end", columnList="courtId,startsAt,endsAt")
})
public class Booking {
  @Id @GeneratedValue
  private UUID id;

  @Column(nullable = false)
  private UUID userId;

  @Column(nullable = false)
  private UUID courtId;

  @Column(nullable = false)
  private Instant startsAt; 

  @Column(nullable = false)
  private Instant endsAt;    

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Status status = Status.CONFIRMED;

  public enum Status { CONFIRMED, CANCELED }

  // --- getters & setters ---

  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }

  public UUID getUserId() { return userId; }
  public void setUserId(UUID userId) { this.userId = userId; }

  public UUID getCourtId() { return courtId; }
  public void setCourtId(UUID courtId) { this.courtId = courtId; }

  public Instant getStartsAt() { return startsAt; }
  public void setStartsAt(Instant startsAt) { this.startsAt = startsAt; }

  public Instant getEndsAt() { return endsAt; }
  public void setEndsAt(Instant endsAt) { this.endsAt = endsAt; }

  public Status getStatus() { return status; }
  public void setStatus(Status status) { this.status = status; }
}
