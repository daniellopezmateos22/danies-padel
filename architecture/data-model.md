# Data Model — Danie’s Padel (alto nivel)

Este documento describe las entidades principales y sus relaciones para el MVP.

## Visión general
- Un **club único** con varias **pistas** y **franjas** horarias.
- Usuarios con roles (ADMIN, STAFF, PLAYER).
- Reservas de pista, partidas abiertas (4 jugadores) y eventos con inscripciones.

## Entidades

### User
- `id` (uuid)
- `name` (string)
- `email` (string, único)
- `role` (enum: ADMIN | STAFF | PLAYER)
- `level` (int 1–7)
- `status` (enum: ACTIVE | INACTIVE)
- `createdAt` (datetime)
- `updatedAt` (datetime)

### Court
- `id` (uuid)
- `name` (string) — p.ej. “Pista 1”
- `surface` (string, opcional)
- `status` (enum: OPEN | MAINTENANCE)
- `createdAt` (datetime)

### TimeSlot
> Representa una franja concreta en una pista, utilizable para reservas o partidas.
- `id` (uuid)
- `courtId` (ref → Court)
- `startsAt`, `endsAt` (datetime)
- `price` (decimal, opcional, informativo en MVP)
- `state` (enum: AVAILABLE | HELD | BOOKED | BLOCKED)
- `createdAt` (datetime)

### Booking
> Reserva de pista de un usuario.
- `id` (uuid)
- `userId` (ref → User)
- `courtId` (ref → Court)
- `startsAt`, `endsAt` (datetime)
- `status` (enum: ACTIVE | CANCELLED | NO_SHOW)
- `origin` (enum: WEB | STAFF)
- `createdAt` (datetime)
- `cancelledAt` (datetime, opcional)

### OpenMatch
> Partida abierta con capacidad máx. 4 jugadores.
- `id` (uuid)
- `creatorId` (ref → User)
- `courtId` (ref → Court, opcional en creación; se puede asignar al cerrar)
- `startsAt`, `endsAt` (datetime)
- `targetLevel` (int 1–7)
- `status` (enum: OPEN | FULL | CANCELLED | FINISHED)
- `createdAt` (datetime)

### OpenMatchPlayer
> Relación de participación de usuarios en una partida abierta.
- `id` (uuid)
- `matchId` (ref → OpenMatch)
- `userId` (ref → User)
- `team` (enum: A | B, opcional MVP)
- `joinedAt` (datetime)

### Event
> Evento del club (clínic, americano, etc.) con aforo.
- `id` (uuid)
- `title` (string)
- `description` (string, opcional)
- `startsAt`, `endsAt` (datetime)
- `capacity` (int)
- `status` (enum: OPEN | FULL | FINISHED | CANCELLED)
- `createdAt` (datetime)

### Enrollment
> Inscripción de un usuario en un evento.
- `id` (uuid)
- `eventId` (ref → Event)
- `userId` (ref → User)
- `status` (enum: ENROLLED | CANCELLED)
- `createdAt` (datetime)

### Notification
> Registro de notificaciones enviadas (email).
- `id` (uuid)
- `type` (enum: BOOKING_CONFIRMED | MATCH_CONFIRMED | REMINDER)
- `userId` (ref → User)
- `relatedId` (uuid — bookingId | matchId | eventId)
- `sentAt` (datetime)
- `status` (enum: SENT | FAILED)

## Relaciones clave
- User (1) — (N) Booking
- User (1) — (N) OpenMatch (como creador)
- OpenMatch (1) — (N) OpenMatchPlayer — (N) User
- Event (1) — (N) Enrollment — (N) User
- Court (1) — (N) TimeSlot
- Court (1) — (N) Booking / OpenMatch
