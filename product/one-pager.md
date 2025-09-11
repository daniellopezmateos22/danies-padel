# One-Pager — Danie’s Padel

## 🌟 Resumen
Danie’s Padel es la **plataforma interna de un único club de pádel** para gestionar reservas de pista, partidas abiertas entre socios, eventos internos y panel de administración.  
El objetivo es **mejorar la ocupación de las pistas**, reducir la fricción al organizar partidos y ofrecer a socios y staff una experiencia moderna y digital.

---

## 🎯 Problema
- Los clubes pequeños suelen usar sistemas manuales o poco flexibles para reservas.  
- A los socios les cuesta **cerrar partidos con jugadores de nivel similar**.  
- El staff invierte demasiado tiempo en **gestionar cancelaciones, cambios y listas de espera**.

---

## 💡 Solución
- **Calendario digital de reservas** con reglas claras (franjas, cancelaciones, no-show).  
- **Partidas abiertas** internas (4 plazas, nivel objetivo, cierre automático al llenarse).  
- **Eventos del club** con cupos e inscripciones online.  
- **Emails transaccionales automáticos** (confirmaciones, recordatorios, avisos).  
- **Panel de métricas básicas** (ocupación, reservas/semana, ratio de partidas completas).

---

## 👥 Usuarios
- **Admin/Staff**: gestionar pistas, franjas, reservas, usuarios y eventos.  
- **Jugador/Socio**: reservar pista, unirse a partidas abiertas, inscribirse a eventos, ver historial.  
- **Coach**: gestionar agenda de clases y alumnos (futuro V2).

---

## 🛠️ Stack
- **Backend**: Java 21 + Spring Boot 3 (REST API).  
- **Frontend**: Angular 18 (SPA, SSR opcional).  
- **DB**: PostgreSQL (AWS RDS).  
- **Infraestructura en AWS**:  
  - **S3 + CloudFront** → frontend y archivos.  
  - **Elastic Beanstalk** (MVP) o **ECS Fargate** (evolución) → backend.  
  - **SES** → emails.  
  - **Secrets Manager** → credenciales.  
  - **CloudWatch** → logs y métricas.

---

## 🚀 Alcance del MVP
- Reservas de pista.  
- Partidas abiertas (hasta 4 jugadores).  
- Eventos con inscripción.  
- Gestión de usuarios/roles.  
- Emails básicos.  
- Panel de métricas simple.  

**Fuera de MVP:** pagos online, push notifications, ranking/torneos, precios dinámicos.

---

## 📊 Métricas de éxito
- % de **ocupación de pistas** semanal.  
- Nº de **reservas** creadas/canceladas por semana.  
- Nº de **partidas abiertas completadas (4/4)**.  
- Tiempo medio para llenar una partida.  
- Ratio de entrega de emails (>98% vía SES).

---

## 📅 Roadmap inicial
- **Semana 0** → Repositorio y documentación (este one-pager, user stories, scope).  
- **Semana 1** → Diseño funcional (contratos API, wireframes).  
- **Semana 2** → Plan AWS (infra mínima en EB, RDS, SES, S3).  
- **Semana 3** → Inicio del desarrollo (auth, reservas, partidas).  
