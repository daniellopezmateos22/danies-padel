package com.daniespadel.app.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

  @Query("""
     select b from Booking b
     where b.startsAt < :end and b.endsAt > :start
       and (:courtId is null or b.courtId = :courtId)
     order by b.startsAt asc
  """)
  List<Booking> findByRangeAndCourt(Instant start, Instant end, UUID courtId);

  @Query("""
     select b from Booking b
     where b.courtId = :courtId
       and b.status = com.daniespadel.app.booking.Booking$Status.CONFIRMED
       and b.startsAt < :endsAt and b.endsAt > :startsAt
  """)
  List<Booking> findOverlaps(UUID courtId, Instant startsAt, Instant endsAt);

  Optional<Booking> findByIdAndUserId(UUID id, UUID userId);
}
