package com.daniespadel.app.timeslot.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class TimeSlotDtos {

  public record RuleCreate(
      UUID courtId,
      List<Integer> daysOfWeek, 
      LocalTime startTime,
      LocalTime endTime,
      int slotMinutes,
      LocalDate fromDate,
      LocalDate toDate
  ) {}

  public record RuleDto(
      UUID id, UUID courtId, List<Integer> daysOfWeek,
      LocalTime startTime, LocalTime endTime,
      int slotMinutes, LocalDate fromDate, LocalDate toDate
  ) {}

  public record OverrideCreate(
      UUID courtId,
      LocalDate date,
      LocalTime startTime,
      LocalTime endTime,
      String status 
  ) {}

  public record OverrideDto(
      UUID id, UUID courtId, LocalDate date,
      LocalTime startTime, LocalTime endTime, String status
  ) {}
}
