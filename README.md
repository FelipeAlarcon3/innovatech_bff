# InnovaTech BFF

Backend For Frontend (BFF) desarrollado para la solución InnovaTech utilizando arquitectura de microservicios.

## Descripción

Este proyecto corresponde al componente BFF (Backend For Frontend) encargado de centralizar la comunicación entre el frontend y los microservicios de la plataforma.

Su objetivo es simplificar el consumo de APIs por parte del cliente, gestionar las solicitudes hacia los microservicios y entregar respuestas unificadas.

## Arquitectura

El sistema está compuesto por:

- Frontend
- BFF (este repositorio)
- Microservicio de Gestión de Usuarios
- Microservicio de Gestión de Recursos
- Base de Datos

```text
Frontend
    │
    ▼
InnovaTech BFF
    │
    ├── Microservicio Usuarios
    │       │
    │       └── Base de Datos
    │
    └── Microservicio Recursos
            │
            └── Base de Datos
