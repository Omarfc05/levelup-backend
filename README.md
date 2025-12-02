# LevelUp Gamer Backend

Proyecto Spring Boot 3 + Java 17 + MySQL para la app LevelUp Gamer.

Incluye:
- Usuarios con roles (USER, ADMIN)
- Autenticación y login con JWT
- CRUD de productos
- CRUD de usuarios (solo ADMIN)
- Protección de rutas /api/admin/**
- Swagger UI en /swagger-ui.html

## Requisitos

- Java 17
- Maven
- MySQL (por ejemplo XAMPP)

Crea una base de datos llamada `levelup_gamer` y ajusta usuario/contraseña en `src/main/resources/application.properties`.

Usuario admin por defecto:

- Email: admin@levelup.cl
- Password: admin123
