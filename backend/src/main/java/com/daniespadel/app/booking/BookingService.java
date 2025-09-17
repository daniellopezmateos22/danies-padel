package com.daniespadel.app.booking;

import com.daniespadel.app.booking.dto.BookingCreateDto;
import com.daniespadel.app.booking.dto.BookingDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {
  private final BookingRepository repo;

  public BookingService(BookingRepository repo) { this.repo = repo; }

  @Transactional(readOnly = true)
  public List<BookingDto> listByDay(LocalDate date, UUID courtId) {
    Instant start = date.atStartOfDay(ZoneOffset.UTC).toInstant();
    Instant end   = date.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();
    return repo.findByRangeAndCourt(start, end, courtId)
               .stream()
               .map(this::toDto)   // <<--- OJO: toDto, no map
               .toList();
  }

  @Transactional(readOnly = true)
  public Optional<BookingDto> getByIdVisibleTo(UUID bookingId, UUID requesterId, boolean isAdmin) {
    return repo.findById(bookingId).flatMap(b -> {
      if (isAdmin || Objects.equals(b.getUserId(), requesterId)) { // <<--- userId, no getUser()
        return Optional.of(toDto(b));
      }
      return Optional.empty();
    });
  }

  /**
   * Lista reservas por usuario.
   * Si no hay rango:
   * - para usuario normal → desde hoy (UTC) hasta +30 días
   * - para admin → desde hoy-30 días hasta +30 días
   */
  @Transactional(readOnly = true)
  public List<BookingDto> listByUser(UUID userId,
                                     Optional<LocalDate> from,
                                     Optional<LocalDate> to,
                                     boolean admin) {

    Instant start;
    Instant end;

    if (from.isPresent() || to.isPresent()) {
      LocalDate fromDate = from.orElse(LocalDate.now(ZoneOffset.UTC).minusDays(30));
      LocalDate toDate   = to.orElse(LocalDate.now(ZoneOffset.UTC).plusDays(30));
      start = fromDate.atStartOfDay(ZoneOffset.UTC).toInstant();
      end   = toDate.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant(); // fin de día exclusivo
    } else {
      if (admin) {
        start = LocalDate.now(ZoneOffset.UTC).minusDays(30).atStartOfDay(ZoneOffset.UTC).toInstant();
      } else {
        start = LocalDate.now(ZoneOffset.UTC).atStartOfDay(ZoneOffset.UTC).toInstant();
      }
      end = LocalDate.now(ZoneOffset.UTC).plusDays(30).plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();
    }

    return repo.findByUserIdAndStartsAtBetweenOrderByStartsAtAsc(userId, start, end)
               .stream()
               .map(this::toDto)   // <<--- toDto
               .toList();
  }

  @Transactional
  public BookingDto create(UUID userId, BookingCreateDto in) {
    if (in.startsAt() == null || in.endsAt() == null || in.courtId() == null)
      throw new IllegalArgumentException("courtId, startsAt, endsAt requeridos");
    if (!in.endsAt().isAfter(in.startsAt()))
      throw new IllegalArgumentException("endsAt debe ser posterior a startsAt");

    var overlaps = repo.findOverlaps(in.courtId(), in.startsAt(), in.endsAt());
    if (!overlaps.isEmpty()) throw new IllegalStateException("La franja ya está ocupada");

    var b = new Booking();
    b.setUserId(userId);                // <<--- userId
    b.setCourtId(in.courtId());
    b.setStartsAt(in.startsAt());
    b.setEndsAt(in.endsAt());
    b.setStatus(Booking.Status.CONFIRMED);

    return toDto(repo.save(b));
  }

  @Transactional
  public void cancel(UUID userId, UUID bookingId, boolean isAdmin) {
    var b = repo.findById(bookingId).orElseThrow();
    if (!isAdmin && !userId.equals(b.getUserId()))   // <<--- userId
      throw new SecurityException("No permitido");
    b.setStatus(Booking.Status.CANCELED);
    repo.save(b);
  }

  private BookingDto toDto(Booking b) {
    return new BookingDto(
        b.getId(), b.getUserId(), b.getCourtId(),
        b.getStartsAt(), b.getEndsAt(), b.getStatus().name()
    );
  }
}
