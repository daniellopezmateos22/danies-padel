package com.daniespadel.app.timeslot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TimeSlotOverrideRepository extends JpaRepository<TimeSlotOverride, UUID> {

  @Query("""
    select o from TimeSlotOverride o
    where (:courtId is null or o.courtId = :courtId)
      and o.date = :date
  """)
  List<TimeSlotOverride> findForDate(UUID courtId, LocalDate date);
}
