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

Funciones principales:

- Registro y gestión de dispensaciones de recetas médicas.
- Validación de rol mediante `X-ROLE` (solo `FARMACEUTICO`).
- Validación de estado de receta previa a dispensación (`STOCK_RESERVADO`).
- Cierre de ciclo: actualización a estado `DISPENSADA`.
- Manejo de errores.
- Persistencia con H2.
- Puerto: `8084`
- URL base: `http://localhost:8084/api/dispensaciones`

---

## Guía de Ejecución

### 1. Clonar el repositorio
git clone <URL>

### 2. Compilar y Validar
Desde la raíz del proyecto (~/RecetaYA_grupo11) ejecutar:
mvn clean test 
mvn clean package

### 3. Levantar los Microservicios
Ejecutar cada microservicio en una terminal independiente.

Ejemplo:
cd "ms-auth-01/auth" o "ms-rec-01/rec" etc..
./mvnw spring-boot:run

Repetir el proceso para cada microservicio.

#### Puertos
-ms-auth-01: 8081
-ms-rec-01: 8082
-ms-inv-01: 8083
-ms-disp-01: 8084

#### Configuración JDBC de cada servicio
-ms-auth-01: jdbc:h2:file:./data/authdb
-ms-rec-01: jdbc:h2:file:./data/recetasdb
-ms-inv-01: jdbc:h2:file:./data/inventariodb
-ms-disp-01: jdbc:h2:file:./data/dispensaciondb

La consola H2 de cada microservicio se encuentra en:
http://localhost:<puerto>/h2-console

### 4. Pruebas con Postman
Las colecciones de endpoints se encuentran organizadas por microservicio en el directorio postman/.

#### Cómo importarlas:
1. Abrir Postman.
2. Seleccionar Import.
3. Buscar y seleccionar el archivo .postman_collection.json correspondiente en el directorio postman/.
4. Importar cada una de las 4 colecciones.

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