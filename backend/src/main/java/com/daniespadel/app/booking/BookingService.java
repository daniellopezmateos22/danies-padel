package com.daniespadel.app.booking;

import com.daniespadel.app.booking.dto.BookingCreateDto;
import com.daniespadel.app.booking.dto.BookingDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
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
      .stream().map(this::toDto).toList();
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
    b.setUserId(userId);
    b.setCourtId(in.courtId());
    b.setStartsAt(in.startsAt());
    b.setEndsAt(in.endsAt());
    b.setStatus(Booking.Status.CONFIRMED);

    return toDto(repo.save(b));
  }

  @Transactional
  public void cancel(UUID userId, UUID bookingId, boolean isAdmin) {
    var b = repo.findById(bookingId).orElseThrow();
    if (!isAdmin && !userId.equals(b.getUserId()))
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
