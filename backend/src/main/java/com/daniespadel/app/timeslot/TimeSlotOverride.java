package com.daniespadel.app.timeslot;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "timeslot_overrides")
public class TimeSlotOverride {

  public enum Status { BLOCKED, OPEN }

  @Id @GeneratedValue
  private UUID id;

  @Column(nullable = false)
  private UUID courtId;

  @Column(nullable = false)
  private LocalDate date;

  @Column(nullable = false)
  private LocalTime startTime;

  @Column(nullable = false)
  private LocalTime endTime;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status;

  // getters/setters
  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }
  public UUID getCourtId() { return courtId; }
  public void setCourtId(UUID courtId) { this.courtId = courtId; }
  public LocalDate getDate() { return date; }
  public void setDate(LocalDate date) { this.date = date; }
  public LocalTime getStartTime() { return startTime; }
  public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
  public LocalTime getEndTime() { return endTime; }
  public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
  public Status getStatus() { return status; }
  public void setStatus(Status status) { this.status = status; }
}
