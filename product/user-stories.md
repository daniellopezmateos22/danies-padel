# User Stories — Danie’s Padel (MVP)

Historias de usuario definidas para el MVP de la plataforma de gestión del club único.

---

## 👥 Roles principales
- **Admin/Staff**
- **Jugador/Socio**
- **Coach** (futuro V2)

---

## 🟢 Autenticación y usuarios
- **Registro/Login**
  - Como **jugador**, quiero registrarme/iniciar sesión para acceder a mis reservas y partidas.
- **Gestión de perfil**
  - Como **jugador**, quiero editar mis datos básicos (nombre, email, nivel) para mantener mi perfil actualizado.
- **Gestión de usuarios (staff)**
  - Como **admin**, quiero dar de alta/baja usuarios para controlar el acceso al club.
  - Como **staff**, quiero asignar roles (jugador, coach, admin) para definir permisos.

---

## 🟢 Reservas de pista
- Como **jugador**, quiero reservar una pista en una franja disponible para asegurar mi plaza.  
- Como **jugador**, quiero cancelar mi reserva dentro del tiempo permitido para no penalizarme.  
- Como **staff**, quiero ver el calendario de todas las pistas para gestionar la ocupación.  
- Como **admin**, quiero definir reglas de reservas (máx. activas, ventana de cancelación) para controlar el uso justo.

---

## 🟢 Partidas abiertas
- Como **jugador**, quiero crear una partida abierta con fecha, hora y nivel objetivo para invitar a otros socios.  
- Como **jugador**, quiero unirme a una partida abierta para completar un cuarteto fácilmente.  
- Como **jugador**, quiero abandonar una partida antes de que se cierre para liberar mi plaza.  
- Como **sistema**, quiero cerrar automáticamente una partida cuando llegue a 4 jugadores para confirmar el partido.

---

## 🟢 Eventos del club
- Como **admin**, quiero crear un evento con título, fecha, descripción y aforo para anunciar actividades del club.  
- Como **jugador**, quiero inscribirme en un evento para participar en actividades especiales.  
- Como **jugador**, quiero ver los detalles de los eventos en los que estoy inscrito para organizarme.

---

## 🟢 Notificaciones y comunicación
- Como **jugador**, quiero recibir un email de confirmación al reservar o unirme a una partida para tener un comprobante.  
- Como **jugador**, quiero recibir un recordatorio por email antes de mi reserva para no olvidarla.  
- Como **staff**, quiero que el sistema envíe avisos automáticos de cambios/cancelaciones para reducir llamadas manuales.

---

## 🟢 Métricas y panel de control
- Como **admin**, quiero ver estadísticas de ocupación semanal de pistas para medir la eficiencia.  
- Como **admin**, quiero ver cuántas partidas abiertas se completan (4/4) para entender la actividad social del club.  
- Como **staff**, quiero descargar un resumen simple de reservas y cancelaciones para control administrativo.

---

## 📌 Notas
- Historias priorizadas para **MVP**: autenticación básica, reservas, partidas abiertas, eventos y notificaciones por email.  
- Historias de **Coach** se consideran parte de **V2** (gestión de clases y alumnos).  
