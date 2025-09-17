package com.daniespadel.app.timeslot;

import com.daniespadel.app.booking.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TimeSlotService {

  public record Slot(UUID courtId, Instant startsAt, Instant endsAt, boolean available, String reason) {}

  private final TimeSlotRuleRepository ruleRepo;
  private final TimeSlotOverrideRepository overrideRepo;
  private final BookingRepository bookingRepo;

  public TimeSlotService(TimeSlotRuleRepository ruleRepo, TimeSlotOverrideRepository overrideRepo, BookingRepository bookingRepo) {
    this.ruleRepo = ruleRepo;
    this.overrideRepo = overrideRepo;
    this.bookingRepo = bookingRepo;
  }

  /**
   * Genera slots para un día combinando reglas + overrides + reservas existentes.
   */
  @Transactional(readOnly = true)
  public List<Slot> getSlotsFor(LocalDate date, Optional<UUID> courtIdOpt) {
    UUID courtId = courtIdOpt.orElse(null);
    int dow = date.getDayOfWeek().getValue(); // 1..7 ISO

    // 1) Cargar reglas activas para el día
    var rules = ruleRepo.findActiveForDate(courtId, dow, date);

    // 2) Generar slots por regla (en hora local) y convertirlos a Instant (UTC)
    List<Slot> ruleSlots = new ArrayList<>();
    for (var r : rules) {
      LocalTime t = r.getStartTime();
      while (!t.plusMinutes(r.getSlotMinutes()).isAfter(r.getEndTime())) {
        var startLt = LocalDateTime.of(date, t);
        var endLt = startLt.plusMinutes(r.getSlotMinutes());
        // Asumimos zona UTC para simplificar; ajusta a ZoneId del club si la tienes
        Instant start = startLt.toInstant(ZoneOffset.UTC);
        Instant end   = endLt.toInstant(ZoneOffset.UTC);
        ruleSlots.add(new Slot(r.getCourtId(), start, end, true, null));
        t = t.plusMinutes(r.getSlotMinutes());
      }
    }

    if (ruleSlots.isEmpty()) return List.of();

    // 3) Overrides del día
    Map<UUID, List<TimeSlotOverride>> overridesByCourt = overrideRepo
        .findForDate(courtId, date)
        .stream()
        .collect(Collectors.groupingBy(TimeSlotOverride::getCourtId));

    // 4) Bookings existentes del día (para marcar ocupados)
    Instant dayStart = date.atStartOfDay(ZoneOffset.UTC).toInstant();
    Instant dayEnd   = date.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();
    var bookings = bookingRepo.findByRangeAndCourt(dayStart, dayEnd, courtId);
    record Window(Instant s, Instant e) {}
    Map<UUID, List<Window>> bookedByCourt = bookings.stream()
        .map(b -> Map.entry(b.getCourtId(), new Window(b.getStartsAt(), b.getEndsAt())))
        .collect(Collectors.groupingBy(Map.Entry::getKey,
            Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

    // 5) Aplicar overrides y bookings
    List<Slot> finalSlots = new ArrayList<>(ruleSlots.size());
    for (var s : ruleSlots) {
      boolean available = true;
      String reason = null;

      // Overrides
      var ovs = overridesByCourt.getOrDefault(s.courtId(), List.of());
      for (var ov : ovs) {
        Instant oStart = LocalDateTime.of(ov.getDate(), ov.getStartTime()).toInstant(ZoneOffset.UTC);
        Instant oEnd   = LocalDateTime.of(ov.getDate(), ov.getEndTime()).toInstant(ZoneOffset.UTC);
        boolean overlap = s.startsAt().isBefore(oEnd) && s.endsAt().isAfter(oStart);
        if (overlap) {
          if (ov.getStatus() == TimeSlotOverride.Status.BLOCKED) {
            available = false; reason = "BLOCKED";
            break;
          }
          // Si un día implementas OPEN que “abre” fuera de regla, aquí podrías permitirlo.
        }
      }

      // Bookings
      if (available) {
        var booked = bookedByCourt.getOrDefault(s.courtId(), List.of());
        for (var w : booked) {
          boolean overlap = s.startsAt().isBefore(w.e()) && s.endsAt().isAfter(w.s());
          if (overlap) { available = false; reason = "BOOKED"; break; }
        }
      }

      finalSlots.add(new Slot(s.courtId(), s.startsAt(), s.endsAt(), available, reason));
    }

    // Orden por pista y hora
    finalSlots.sort(Comparator
        .comparing(Slot::courtId, Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparing(Slot::startsAt));

    return finalSlots;
  }
}
