package com.daniespadel.app.timeslot;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Entity
@Table(name = "timeslot_rules")
public class TimeSlotRule {

  @Id @GeneratedValue
  private UUID id;

  @Column(nullable = false)
  private UUID courtId;

  // ISO-8601 1..7 (1 = Monday)
  @ElementCollection
  @CollectionTable(name = "timeslot_rule_days", joinColumns = @JoinColumn(name = "rule_id"))
  @Column(name = "day_of_week", nullable = false)
  private List<Integer> daysOfWeek = new ArrayList<>();

  @Column(nullable = false)
  private LocalTime startTime;

  @Column(nullable = false)
  private LocalTime endTime;

  @Column(nullable = false)
  private int slotMinutes;

  private LocalDate fromDate; // inclusive (nullable = siempre)
  private LocalDate toDate;   // inclusive (nullable = siempre)

  // getters/setters
  public UUID getId() { return id; }
  public void setId(UUID id) { this.id = id; }
  public UUID getCourtId() { return courtId; }
  public void setCourtId(UUID courtId) { this.courtId = courtId; }
  public List<Integer> getDaysOfWeek() { return daysOfWeek; }
  public void setDaysOfWeek(List<Integer> daysOfWeek) { this.daysOfWeek = daysOfWeek; }
  public LocalTime getStartTime() { return startTime; }
  public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
  public LocalTime getEndTime() { return endTime; }
  public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
  public int getSlotMinutes() { return slotMinutes; }
  public void setSlotMinutes(int slotMinutes) { this.slotMinutes = slotMinutes; }
  public LocalDate getFromDate() { return fromDate; }
  public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }
  public LocalDate getToDate() { return toDate; }
  public void setToDate(LocalDate toDate) { this.toDate = toDate; }
}
