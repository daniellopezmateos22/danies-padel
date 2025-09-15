package com.daniespadel.app.booking.dto;

import java.time.Instant;
import java.util.UUID;

public record BookingDto(
    UUID id,
    UUID userId,
    UUID courtId,
    Instant startsAt,
    Instant endsAt,
    String status
) {}
