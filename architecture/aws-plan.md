
---

## 📄 `docs/architecture/aws-plan.md`

```markdown
# AWS Plan — Danie’s Padel (MVP)

## 1) Objetivo
Desplegar el MVP con **coste bajo**, **seguridad básica** y camino de **escalabilidad** sin rehacer.

## 2) Componentes (MVP)
- **Frontend**: Angular estático en **S3** detrás de **CloudFront**.
- **Backend**: Spring Boot en **Elastic Beanstalk (EB)** (un entorno).
- **Base de datos**: **RDS PostgreSQL** (privado).
- **Emails**: **SES** (dominio verificado).
- **Archivos**: **S3** (uploads de usuarios).
- **Secretos**: **Secrets Manager** (DB creds, JWT).
- **Logs/Métricas**: **CloudWatch**.
- (Opcional) **Route 53** + **ACM** para dominio y TLS.

## 3) Red y seguridad
- **VPC** (default o dedicada):
  - Subredes públicas: **ALB/EB**.
  - Subredes privadas: **RDS**.
- **Security Groups**:
  - ALB → EB: 80/443.
  - EB → RDS: 5432.
  - Bloquear resto por defecto.
- **TLS**:
  - **ACM** cert para CloudFront y ALB.
  - Forzar HTTPS.

## 4) IAM
- **Role EB (instance profile)**:
  - CloudWatch Logs (escritura).
  - Secrets Manager: `GetSecretValue` SOLO para secretos del proyecto.
  - S3 (si backend genera presigned): acceso limitado a bucket/prefijos.
- **Role/usuario CI** (GitHub OIDC o access keys):
  - S3: sync del build FE.
  - EB: crear versión + actualizar entorno.
  - (Futuro) ECR/ECS si migramos a contenedores.

## 5) Datos y secretos
- **RDS Postgres**:
  - Clase `db.t3.micro` (dev).
  - Storage 20–30 GB (GP3).
  - Backups automáticos (7 días).
  - Deletion protection ON en prod.
- **Secrets Manager**:
  - `dp/rds/app` → host, db, user, pass.
  - `dp/jwt/secret` → clave JWT.
- **S3**:
  - `dp-frontend-<env>` (hosting estático).
  - `dp-uploads-<env>` (privado, acceso por presigned URL).
  - CloudFront solo para **frontend** (no para uploads privados).
- **SES**:
  - Verificar dominio y remitente.
  - Salir de sandbox si se necesita envío a direcciones externas.

## 6) Despliegue
- **Frontend**:
  - Build Angular → **sync a S3** → **invalidación CloudFront**.
- **Backend**:
  - EB (plataforma Java 21 o Docker 1 contenedor).
  - Healthcheck `/actuator/health`.
  - Vars de entorno → leer **Secrets Manager**.
- **Dominio (opcional)**:
  - `app.daniespadel.com` → ALB (backend).
  - `daniespadel.com` / `www` → CloudFront (frontend).

## 7) Observabilidad
- **CloudWatch Logs**:
  - Logs EB (app y sistema).
  - (Opcional) ALB access logs.
- **Métricas y alarmas**:
  - EB: CPU, latency.
  - RDS: CPU, conexiones.
  - Alarmas sencillas (CPU > 70%, conexiones > umbral).
- (V2) Trazas con X-Ray / OpenTelemetry.

## 8) Cost control
- 1 instancia EB (tamaño bajo).
- Retención de logs 7–14 días en dev.
- Apagar entornos no usados.
- Evitar buckets públicos (solo CloudFront para FE).

## 9) Roadmap de infra
- **MVP**: S3+CF (FE), EB (BE), RDS, SES, Secrets, CloudWatch.
- **Pro**: ECS Fargate + ECR, SQS para colas (emails/recordatorios), Cognito (Auth).

## 10) Checklist
- [ ] Buckets S3 (frontend, uploads)
- [ ] CloudFront + ACM (FE)
- [ ] EB app + environment (BE)
- [ ] RDS Postgres (privado) + SG
- [ ] Secrets (`dp/rds/app`, `dp/jwt/secret`)
- [ ] SES dominio y remitente verificados
- [ ] Vars de entorno EB
- [ ] Retención logs y alarmas CloudWatch
- [ ] (Opcional) Route 53 + DNS
