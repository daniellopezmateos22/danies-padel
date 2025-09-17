package com.daniespadel.app.timeslot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TimeSlotRuleRepository extends JpaRepository<TimeSlotRule, UUID> {

  List<TimeSlotRule> findByCourtId(UUID courtId);

  @Query("""
    select r from TimeSlotRule r
    where (:courtId is null or r.courtId = :courtId)
      and (:dayOfWeek member of r.daysOfWeek)
      and (r.fromDate is null or r.fromDate <= :date)
      and (r.toDate   is null or r.toDate   >= :date)
  """)
  List<TimeSlotRule> findActiveForDate(UUID courtId, Integer dayOfWeek, LocalDate date);
}
