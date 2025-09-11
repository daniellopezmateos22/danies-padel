package com.daniespadel.app.timeslot;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
public class TimeSlotController {
  private final TimeSlotService service;

  public TimeSlotController(TimeSlotService service) { this.service = service; }

  @GetMapping("/timeslots")
  public Map<String, Object> list(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
      @RequestParam(required = false) UUID courtId
  ) {
    return Map.of("data", service.getSlotsFor(date, Optional.ofNullable(courtId)));
  }
}
