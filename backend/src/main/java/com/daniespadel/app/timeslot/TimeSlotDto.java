package com.daniespadel.app.timeslot;

import java.time.Instant;
import java.util.UUID;

public record TimeSlotDto(
    String id,
    UUID courtId,
    Instant startsAt,
    Instant endsAt,
    String state
) {}
