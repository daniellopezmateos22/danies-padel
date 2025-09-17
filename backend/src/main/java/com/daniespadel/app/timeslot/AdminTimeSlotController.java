package com.daniespadel.app.timeslot;

import com.daniespadel.app.timeslot.dto.TimeSlotDtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/timeslots")
@PreAuthorize("hasRole('ADMIN')")
public class AdminTimeSlotController {

  private final TimeSlotRuleRepository rules;
  private final TimeSlotOverrideRepository overrides;

  public AdminTimeSlotController(TimeSlotRuleRepository rules, TimeSlotOverrideRepository overrides) {
    this.rules = rules;
    this.overrides = overrides;
  }

  // ---- RULES ----
  @PostMapping("/rules")
  @ResponseStatus(HttpStatus.CREATED)
  public RuleDto createRule(@RequestBody RuleCreate in) {
    var r = new TimeSlotRule();
    r.setCourtId(in.courtId());
    r.setDaysOfWeek(in.daysOfWeek());
    r.setStartTime(in.startTime());
    r.setEndTime(in.endTime());
    r.setSlotMinutes(in.slotMinutes());
    r.setFromDate(in.fromDate());
    r.setToDate(in.toDate());
    var saved = rules.save(r);
    return toDto(saved);
  }

  @GetMapping("/rules")
  public List<RuleDto> listRules(@RequestParam(required = false) UUID courtId) {
    var list = (courtId == null) ? rules.findAll() : rules.findByCourtId(courtId);
    return list.stream().map(this::toDto).toList();
  }

  @PutMapping("/rules/{id}")
  public RuleDto updateRule(@PathVariable UUID id, @RequestBody RuleCreate in) {
    var r = rules.findById(id).orElseThrow();
    r.setCourtId(in.courtId());
    r.setDaysOfWeek(in.daysOfWeek());
    r.setStartTime(in.startTime());
    r.setEndTime(in.endTime());
    r.setSlotMinutes(in.slotMinutes());
    r.setFromDate(in.fromDate());
    r.setToDate(in.toDate());
    return toDto(rules.save(r));
  }

  @DeleteMapping("/rules/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteRule(@PathVariable UUID id) {
    rules.deleteById(id);
  }

  private RuleDto toDto(TimeSlotRule r) {
    return new RuleDto(r.getId(), r.getCourtId(), r.getDaysOfWeek(),
        r.getStartTime(), r.getEndTime(), r.getSlotMinutes(), r.getFromDate(), r.getToDate());
  }

  // ---- OVERRIDES ----
  @PostMapping("/overrides")
  @ResponseStatus(HttpStatus.CREATED)
  public OverrideDto createOverride(@RequestBody OverrideCreate in) {
    var o = new TimeSlotOverride();
    o.setCourtId(in.courtId());
    o.setDate(in.date());
    o.setStartTime(in.startTime());
    o.setEndTime(in.endTime());
    o.setStatus(TimeSlotOverride.Status.valueOf(in.status()));
    return toDto(overrides.save(o));
  }

  @GetMapping("/overrides")
  public List<OverrideDto> listOverrides(
      @RequestParam LocalDate date,
      @RequestParam(required = false) UUID courtId
  ) {
    return overrides.findForDate(courtId, date).stream().map(this::toDto).toList();
  }

  @DeleteMapping("/overrides/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteOverride(@PathVariable UUID id) {
    overrides.deleteById(id);
  }

  private OverrideDto toDto(TimeSlotOverride o) {
    return new OverrideDto(o.getId(), o.getCourtId(), o.getDate(),
        o.getStartTime(), o.getEndTime(), o.getStatus().name());
  }
}
