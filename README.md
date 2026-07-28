# Materiales API

API REST desarrollada como solución para la prueba técnica de Gestión de Materiales.

## Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- MySQL
- MapStruct
- Swagger / OpenAPI
- Lombok
- Maven
- JUnit 5
- Mockito
- Angular

---

## Funcionalidades

- Autenticación mediante JWT.
- CRUD de materiales.
- Búsqueda por tipo.
- Búsqueda por ciudad.
- Búsqueda por fecha de compra.
- Documentación con Swagger.
- Manejo global de excepciones.
- Respuesta estándar mediante ApiResponseDTO.
- Validaciones de negocio.
- Pruebas unitarias.

---

## Requisitos

- Java 17
- Spring Boot 3.5
- Maven
- MySQL
- Angular 

---

## Configuración

La aplicación utiliza **Spring Profiles** para separar la configuración por ambiente.

Para desarrollo se utiliza:

```
application-local.properties
```

---

## Usuario de prueba

```
Usuario: admin
Contraseña: admin123
```

---

## Swagger

Una vez iniciada la aplicación:

```
http://localhost:8080/swagger-ui/index.html
```

Para consumir los endpoints protegidos:

1. Ejecutar `/api/auth/login`.
2. Copiar el JWT recibido.
3. Presionar **Authorize** en Swagger.
4. Ingresar:

```
Bearer <token>
```

---

## Frontend

La aplicación Angular consume la API utilizando JWT.

Para iniciar el proyecto:

```bash
npm install
ng serve -o
```

Acceso:

```
http://localhost:4200
```

---

## Autor

Johaymen Álvarez Romero