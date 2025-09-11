# Data Model — Danie’s Padel (alto nivel)

## User
- id (uuid)
- name
- email (único)
- role {ADMIN, STAFF, PLAYER}
- level (1–7)
- status {ACTIVE, INACTIVE}
- createdAt

## Court
- id
- name (Pista 1, Pista 2…)
- surface (opcional)
- status {OPEN, MAINTENANCE}

## TimeSlot
- id
- courtId → Court
- startsAt, endsAt
- price (decimal) [MVP: informativo]
- state {AVAILABLE, HELD, BOOKED, BLOCKED}

## Booking
- id
- userId → User
- courtId → Court
- startsAt, endsAt
- status {ACTIVE, CANCELLED, NO_SHOW}
- origin {WEB, STAFF}
- createdAt, cancelledAt?

## OpenMatch
- id
- creatorId → User
- courtId? (opcional; si null, se asigna al cerrar) 
- startsAt, endsAt
- targetLevel (1–7)
- status {OPEN, FULL, CANCELLED, FINISHED}
- createdAt

## OpenMatchPlayer
- id
- matchId → OpenMatch
- userId → User
- team {A,B}? (opcional MVP)

## Event
- id
- title, description
- startsAt, endsAt
- capacity (int)
- status {OPEN, FULL, FINISHED, CANCELLED}

## Enrollment
- id
- eventId → Event
- userId → User
- status {ENROLLED, CANCELLED}

## Notification
- id
- type {BOOKING_CONFIRMED, MATCH_CONFIRMED, REMINDER}
- userId → User
- relatedId (bookingId/matchId/eventId)
- sentAt, status {SENT, FAILED}
