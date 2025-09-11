# Contribuir a Danie’s Padel

¡Gracias por tu interés! Este documento explica cómo proponer mejoras, reportar bugs y abrir PRs.

## Tabla de contenidos
1. [Flujo de trabajo](#flujo-de-trabajo)
2. [Issues](#issues)
3. [Ramas y commits](#ramas-y-commits)
4. [Pull Requests](#pull-requests)
5. [Estándares de proyecto](#estándares-de-proyecto)
6. [Comunicación](#comunicación)
7. [Licencia](#licencia)

---

## Flujo de trabajo
- Usamos **GitHub Flow**: ramas cortas desde `main` → PR → revisión → merge.
- Todo cambio empieza como **Issue** (bug/feature/docs) con contexto y criterios de aceptación.
- Asigna etiquetas `type:*` y `area:*` (backend, frontend, infra, docs).

## Issues
- **Bug**: describe pasos para reproducir, resultado esperado vs. actual, capturas/logs.
- **Feature**: explica el problema, el valor para el usuario y una propuesta de solución.
- Busca duplicados antes de crear uno nuevo.

## Ramas y commits
- Nombra ramas: `feature/<breve-descripcion>`, `fix/<breve-descripcion>`, `docs/<tema>`.
- **Conventional Commits**:
  - `feat: permitir crear partidas abiertas`
  - `fix: corregir cálculo de ocupación semanal`
  - `docs: añadir one-pager`
  - `chore: configurar CI`
- Mensajes en infinitivo, en español o inglés, concisos. Usa cuerpo del commit para el “por qué”.

## Pull Requests
- Pequeños y enfocados (ideal < 300 líneas).
- Incluye en la descripción: **contexto**, **qué cambia**, **cómo probar**, **riesgos**.
- Checklist sugerido:
  - [ ] Hay Issue vinculado (`Closes #123`)
  - [ ] Se actualizó documentación si aplica
  - [ ] Se añadieron/ajustaron pruebas (cuando haya test suite)
  - [ ] Pasa linters/formatters
- Responde feedback con respeto. Se permite “request changes” para mantener calidad.

## Estándares de proyecto
- **Monorepo** con carpetas `backend/`, `frontend/`, `infra/`, `docs/`.
- **Backend (Java + Spring Boot)**: seguir convenciones de Spring, endpoints REST claros, validaciones y manejo de errores consistente.
- **Frontend (Angular)**: modularización por features, servicios para API, componentes presentacionales vs. contenedores cuando aplique.
- **Infra (AWS)**: documentación en `docs/architecture/` (EB para MVP; ECS en evolución). Secrets en **AWS Secrets Manager**.
- **Estilo**: formateadores y linters (se documentarán cuando se añada código). Preferimos Pull Requests con cambios mínimos por commit.

## Comunicación
- Canales: Issues y PRs en GitHub.
- Respeta el **Código de Conducta**.
- Dudas generales: abre un Issue `question:` o escribe a **daniellopezmateos22@gmail.com**.

## Licencia
Al contribuir, aceptas que tus aportes se publiquen bajo la licencia **MIT** del repositorio.
