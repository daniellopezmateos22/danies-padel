package com.daniespadel.app.timeslot;

import com.daniespadel.app.court.Court;
import com.daniespadel.app.court.CourtRepository;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
public class TimeSlotService {
  private final CourtRepository courts;

  // Política MVP (puedes mover a config)
  private static final LocalTime OPEN = LocalTime.of(9,0);
  private static final LocalTime CLOSE = LocalTime.of(22,0);
  private static final int SLOT_MIN = 60;

  public TimeSlotService(CourtRepository courts) {
    this.courts = courts;
  }

  public List<TimeSlotDto> getSlotsFor(LocalDate date, Optional<UUID> courtIdOpt) {
    List<Court> targetCourts = courtIdOpt
        .map(id -> courts.findById(id).map(List::of).orElse(List.of()))
        .orElseGet(courts::findAll);

    ZoneId zone = ZoneId.systemDefault();
    List<TimeSlotDto> out = new ArrayList<>();

    for (Court c : targetCourts) {
      LocalDateTime cur = date.atTime(OPEN);
      while (!cur.isAfter(date.atTime(CLOSE).minusMinutes(SLOT_MIN))) {
        LocalDateTime end = cur.plusMinutes(SLOT_MIN);
        String id = UUID.nameUUIDFromBytes((c.getId().toString() + cur).getBytes()).toString();
        out.add(new TimeSlotDto(
            id,
            c.getId(),
            cur.atZone(zone).toInstant(),
            end.atZone(zone).toInstant(),
            "AVAILABLE" // más adelante: cruzar con bookings
        ));
        cur = end;
      }
    }
    out.sort(Comparator.comparing(TimeSlotDto::startsAt).thenComparing(TimeSlotDto::courtId));
    return out;
  }
}
