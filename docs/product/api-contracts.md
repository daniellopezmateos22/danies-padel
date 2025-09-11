# API Contracts — Danie’s Padel (MVP)

## Auth / Users
- POST   /auth/register      — alta básica de jugador (MVP)*
- POST   /auth/login         — login (JWT)
- GET    /me                 — perfil actual
- PATCH  /me                 — editar perfil (nombre, nivel)

*(Alta de STAFF/ADMIN solo por Admin vía backoffice)  
- POST   /users              — [ADMIN] crear usuario (staff/player)
- PATCH  /users/{id}         — [ADMIN] cambiar rol/estado
- GET    /users              — [STAFF] listar (búsqueda simple)

## Courts & TimeSlots
- GET    /courts             — listar pistas
- GET    /timeslots          — franjas disponibles (filtros: date, courtId)

## Bookings
- POST   /bookings           — crear reserva
- GET    /bookings/mine      — mis reservas
- DELETE /bookings/{id}      — cancelar (reglas de ventana)
- GET    /bookings           — [STAFF] listar por fecha/court

## Open Matches
- POST   /open-matches                 — crear partida abierta
- GET    /open-matches                 — descubrir (filtros: date, level)
- POST   /open-matches/{id}/join       — unirse
- POST   /open-matches/{id}/leave      — salir
- POST   /open-matches/{id}/close      — cierre manual [STAFF]; auto-cierre al 4º
- GET    /open-matches/mine            — mis partidas (creadas/inscrito)

## Events
- POST   /events              — [STAFF] crear evento
- GET    /events              — listar
- POST   /events/{id}/enroll  — inscribirse
- DELETE /events/{id}/enroll  — cancelar inscripción
- GET    /events/mine         — mis inscripciones

## Metrics (básicas)
- GET    /metrics/occupancy?from&to      — ocupación por franja/día
- GET    /metrics/summary?from&to        — reservas, cancelaciones, partidas 4/4

## Notas
- Autenticación por **Bearer JWT** (MVP).
- Respuestas con envoltorio estándar (status, data, error).
- Validaciones server-side y errores con mensaje human-readable.
