# Scope MVP — Danie’s Padel

Definición del alcance del **Minimum Viable Product (MVP)** para la plataforma del club único.

---

## ✅ Funcionalidades incluidas (Sí)
### Usuarios y autenticación
- Registro/Login básico con email y contraseña.
- Roles: **Admin/Staff**, **Jugador/Socio** (Coach en V2).
- Perfil de usuario (datos básicos y nivel).

### Reservas de pista
- Calendario de pistas con franjas horarias.
- Crear, listar y cancelar reservas.
- Reglas básicas de reservas (máx. activas, ventana de cancelación).
- Visualización de reservas por staff/admin.

### Partidas abiertas
- Crear partida abierta (nivel objetivo, fecha/hora).
- Unirse/abandonar partida.
- Cierre automático al llegar a 4 jugadores.

### Eventos
- Crear evento (título, descripción, fecha, aforo).
- Inscripción de jugadores.
- Listado de eventos activos.

### Notificaciones
- Emails automáticos vía AWS SES:
  - Confirmación de reserva.
  - Confirmación/unión a partida.
  - Recordatorios antes de reservas/partidas.

### Métricas básicas
- Ocupación semanal de pistas.
- Nº de reservas creadas/canceladas.
- Nº de partidas abiertas completadas (4/4).

---

## 🚫 Fuera de alcance (No, V2 o posterior)
- Pagos online (Stripe u otros).
- App móvil nativa (Android/iOS).
- Notificaciones push.
- Precios dinámicos / gestión avanzada de tarifas.
- Rankings, torneos oficiales y ladders.
- Agenda de entrenadores / gestión de clases.
- Integración con TPV o sistemas externos.
- Integración con AWS Cognito (se usará JWT simple en MVP).
- Asincronía avanzada (SQS, colas).
- Infraestructura con ECS Fargate (se usará EB en MVP).
- Monitorización avanzada (solo métricas básicas en CloudWatch).

---

## 🎯 Objetivo del MVP
Tener un sistema **funcional y desplegado en AWS** que permita:
1. Gestionar reservas y partidas abiertas en un único club.  
2. Ofrecer inscripciones a eventos.  
3. Comunicar vía email confirmaciones y recordatorios.  
4. Mostrar métricas básicas al admin.  

Esto permitirá validar el producto con **usuarios reales del club** y preparar la base para evoluciones futuras.
