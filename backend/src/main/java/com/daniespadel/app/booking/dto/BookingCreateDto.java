package com.daniespadel.app.booking.dto;

import java.time.Instant;
import java.util.UUID;

public record BookingCreateDto(UUID courtId, Instant startsAt, Instant endsAt) {}
