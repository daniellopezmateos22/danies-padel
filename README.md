# Danie’s Padel

Plataforma para la **gestión de un club único de pádel**: reservas de pista, partidas abiertas internas, eventos de club y panel de administración.  

Stack objetivo: **Java + Spring Boot**, **Angular**, y **AWS** (RDS Postgres, S3/CloudFront, SES, Secrets Manager, Elastic Beanstalk/ECS).

---

## 🎯 Objetivo del MVP
- **Reservas de pista** con calendario del club (franjas y reglas básicas).
- **Partidas abiertas** internas (4 plazas, nivel objetivo, cierre automático).
- **Eventos del club** (cupos e inscripciones).
- **Usuarios** (roles: admin/staff/coach/player) y perfiles básicos.
- **Emails transaccionales** (confirmaciones/recordatorios) vía AWS SES.
- **Métricas**: ocupación, reservas/semana, partidas 4/4.

---

## 👥 Usuarios y casos de uso
- **Admin/Staff**: gestionar pistas, franjas, reservas, usuarios, eventos y ver ocupación.
- **Jugador/Socio**: reservar pista, unirse a partidas abiertas, inscribirse en eventos, ver historial.
- **Coach**: gestionar agenda de clases (para V2).

---

## 🏗️ Arquitectura (alto nivel)
- **Frontend**: Angular (SPA) servido desde **S3 + CloudFront**  
- **Backend**: Spring Boot (API REST)  
- **DB**: PostgreSQL en **AWS RDS**  
- **Emails**: **AWS SES**  
- **Archivos**: **S3** (avatares/carteles)  
- **Auth**: JWT propio en MVP (migrable a Cognito en V2)  
- **Despliegue backend**: **Elastic Beanstalk** (inicial) o **ECS Fargate** (evolución)  
- **Configuración/Secretos**: **AWS Secrets Manager**  
- **Observabilidad**: **CloudWatch**

## 📚 Documentación

- Product
  - [One-pager](docs/product/one-pager.md)
  - [User stories (MVP)](docs/product/user-stories.md)
  - [Scope MVP](docs/product/scope-mvp.md)
  - [Policies del club](docs/product/policies.md) 
- Architecture
  - [Data model (alto nivel)](docs/architecture/data-model.md)
  - [AWS plan](docs/architecture/aws-plan.md)
