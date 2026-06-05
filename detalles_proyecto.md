# Informe del Proyecto: Gym_pro

## 1. Resumen General

| Campo | Valor |
|---|---|
| **Nombre** | Gym_pro |
| **Grupo** | gym_proyecto |
| **Versión** | 0.0.1-SNAPSHOT |
| **Java** | 21 |
| **Spring Boot** | 4.0.6 |
| **Gestor de dependencias** | Maven |
| **Base de datos** | MySQL (`gym_pro_bd`) |
| **ORM** | JPA / Hibernate |
| **Template engine** | Thymeleaf |
| **Estado compilación** | ✅ Éxito |

---

## 2. Estructura del Proyecto

```
Gym_pro/
├── pom.xml
├── mvnw / mvnw.cmd
├── src/
│   ├── main/
│   │   ├── java/gym_proyecto/Gym_pro/
│   │   │   ├── GymProApplication.java
│   │   │   ├── controller/
│   │   │   │   └── ControladorInicio.java
│   │   │   ├── dao/
│   │   │   │   └── PersonaDao.java
│   │   │   ├── model/
│   │   │   │   └── Persona.java
│   │   │   └── service/
│   │   │       ├── PersonaService.java
│   │   │       └── PersonaServiceImpl.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/gym_proyecto/Gym_pro/
│           └── GymProApplicationTests.java
```

---

## 3. Arquitectura (MVC con Capas)

```
Controlador (ControladorInicio)
    ↕
Servicio (PersonaService ← PersonaServiceImpl)
    ↕
DAO / Repositorio (PersonaDao ← JpaRepository)
    ↕
Modelo / Entidad (Persona)
    ↕
Base de Datos MySQL (gym_pro_bd.persona)
```

### 3.1 Capa de Presentación — `ControladorInicio.java`

Controlador Spring MVC con los siguientes endpoints:

| Método | Ruta | Acción |
|---|---|---|
| `GET` | `/` | Lista todas las personas |
| `GET` | `/agregar` | Muestra formulario para nueva persona |
| `POST` | `/guardar` | Guarda persona (con validación `@Valid`) |
| `GET` | `/editar/{idPersona}` | Muestra formulario para editar |
| `GET` | `/eliminar/{idPersona}` | Elimina una persona |

### 3.2 Capa de Servicio — `PersonaService` / `PersonaServiceImpl`

Operaciones CRUD disponibles:

| Método | Descripción |
|---|---|
| `listarPersonas()` | Retorna todas las personas |
| `guardar(Persona)` | Crea o actualiza una persona |
| `eliminar(Persona)` | Elimina una persona por ID |
| `encontrarPersona(Persona)` | Busca una persona por ID |

### 3.3 Capa de Datos — `PersonaDao`

Extiende `JpaRepository<Persona, Long>` — proporciona métodos CRUD sin implementación manual.

### 3.4 Modelo — `Persona.java`

Entidad JPA mapeada a la tabla `persona`:

| Campo | Tipo | Anotaciones |
|---|---|---|
| `idPersona` | `Long` | `@Id`, `@GeneratedValue(IDENTITY)` |
| `nombre` | `String` | — |
| `apellido` | `String` | — |
| `email` | `String` | — |
| `telefono` | `String` | — |

---

## 4. Configuración de Base de Datos

**Archivo:** `src/main/resources/application.properties`

| Propiedad | Valor |
|---|---|
| `spring.datasource.url` | `jdbc:mysql://localhost:3306/gym_pro_bd?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true` |
| `spring.datasource.username` | `root` |
| `spring.datasource.password` | `lokuw` |
| `spring.datasource.driver-class-name` | `com.mysql.cj.jdbc.Driver` |
| `spring.jpa.hibernate.ddl-auto` | `update` |
| `spring.jpa.show-sql` | `true` |

> ⚠️ **Importante:** La base de datos MySQL `gym_pro_bd` debe existir en el servidor `localhost:3306` para que la aplicación funcione correctamente.

---

## 5. Dependencias (pom.xml)

| Dependencia | Propósito |
|---|---|
| `spring-boot-starter-web` | API REST / MVC |
| `spring-boot-starter-thymeleaf` | Template engine HTML |
| `spring-boot-starter-data-jpa` | ORM / JPA / Hibernate |
| `spring-boot-starter-validation` | Validación con `@Valid` |
| `mysql-connector-j` | Conector MySQL |
| `lombok` | Reducción de boilerplate (`@Data`) |
| `spring-boot-devtools` | Recarga automática en desarrollo |
| `spring-boot-starter-test` | Testing (JUnit 5 + Spring Test) |

---

## 6. Pruebas

- **Clase:** `GymProApplicationTests` — prueba de contexto Spring.
- **Estado:** ❌ Fallan por ausencia de la base de datos MySQL.
  - Error: `Unknown database 'gym_pro_bd'`

---

## 7. Problemas Detectados y Soluciones Aplicadas

### 7.1 ✅ Corregidos

| Problema | Solución |
|---|---|
| Dependencia `spring-boot-starter-webmvc` inexistente | Reemplazada por `spring-boot-starter-web` |
| Falta `spring-boot-starter-data-jpa` | Agregada |
| Falta `spring-boot-starter-validation` | Agregada |
| Dependencia `spring-boot-starter-thymeleaf-test` inexistente | Reemplazada por `spring-boot-starter-test` |
| Dependencia `spring-boot-starter-webmvc-test` inexistente | Eliminada |
| Falta import `Persona` en `ControladorInicio.java` | Agregado |
| Falta import `@Valid` en `ControladorInicio.java` | Agregado |
| Falta import `JpaRepository` y `Persona` en `PersonaDao.java` | Agregados |
| Propiedad `spring.jpa.properties.hibernate.dialect` obsoleta | Reemplazada por `spring.jpa.database-platform` |

### 7.2 ⚠️ Pendientes

| Problema | Impacto |
|---|---|
| Base de datos MySQL `gym_pro_bd` no existe | La app no puede iniciar ni pasar tests |
| No existen plantillas Thymeleaf (`index.html`, `modificar.html`) | Error 500 al acceder a rutas del controlador |

---

## 8. Recomendaciones

1. **Crear la base de datos** en MySQL:
   ```sql
   CREATE DATABASE gym_pro_bd;
   ```

2. **Crear plantillas HTML** en `src/main/resources/templates/`:
   - `index.html` — para listar personas
   - `modificar.html` — para crear/editar personas

3. **Para desarrollo sin MySQL**, considerar agregar perfil con H2:
   ```properties
   # application-dev.properties
   spring.datasource.url=jdbc:h2:mem:gym_pro_bd
   spring.datasource.driver-class-name=org.h2.Driver
   spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
   ```
