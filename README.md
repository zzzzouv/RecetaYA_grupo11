# RecetaYA - Microservicios

La solución está construida con una arquitectura basada en microservicios utilizando **Java y Spring Boot**.

## Integrantes

- Eduardo Cortes-Monroy Castro
- Juan Chigne Gutierrez

## Flujo de trabajo

El repositorio utiliza:

- `main`
- `develop` (los cambios se integran mediante Pull Request)
- `feature/*` (ramas de desarrollo)

---

## Resumen del caso

RecetaYa es una cadena de farmacias que busca digitalizar el proceso de emisión, reserva y dispensación de recetas médicas.

El sistema permite:

- Registrar usuarios con roles médicos y farmacéuticos.
- Emitir recetas médicas.
- Consultar recetas y sus medicamentos.
- Reservar stock de medicamentos.
- Dispensar recetas.
- Mantener separados los distintos procesos mediante microservicios.

Los roles principales son:

- `MEDICO`
- `FARMACEUTICO`

Las recetas se registran inicialmente con el estado:

`PENDIENTE_RESERVA`

---

## Arquitectura

El sistema está dividido en cuatro microservicios principales.

### MS-AUTH-01
Encargado de la gestión de usuarios, roles y autenticación simulada.

Funciones principales:

- CRUD de usuarios.
- Roles `MEDICO` y `FARMACEUTICO`.
- Login simulado.
- Validación de roles mediante `X-ROLE`.
- Manejo de errores.
- Persistencia con H2.
- Puerto: `8081`
- URL base: `http://localhost:8081/api/usuarios`

### MS-REC-01
Encargado de la creación y gestión de recetas médicas.

Funciones principales:

- CRUD de recetas.
- Registro de medicamentos y cantidades.
- Estado inicial automático `PENDIENTE_RESERVA`.
- Consulta de recetas por estado.
- Validación para que solo `MEDICO` pueda emitir recetas.
- Manejo de errores.
- Persistencia con H2.
- Puerto: `8082`
- URL base: `http://localhost:8082/api/recetas`

### MS-INV-01
Encargado de la gestión de medicamentos, stock y reserva de inventario.

Funciones principales:

- CRUD de medicamentos.
- Consulta de disponibilidad y stock por sucursal.
- Reserva de stock para recetas médicas.
- Descuento definitivo de stock tras dispensación.
- Manejo de errores.
- Persistencia con H2.
- Puerto: `8083`
- URL base: `http://localhost:8083/api/inventario`

### MS-DISP-01
Encargado del proceso de dispensación de recetas por parte del farmacéutico.

---

## Tecnologías utilizadas

- Java 21
- Spring Boot 4.1.1
- Maven
- Spring Web
- Spring Data JPA
- Hibernate
- H2 Database
- Lombok
- Postman
- Git
- GitHub