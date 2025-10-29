# Ninja Dashboard - Arquitectura Hexagonal

Aplicación Spring Boot con arquitectura hexagonal para gestionar un dashboard de ninjas basado en el universo de Naruto.

## Arquitectura

La aplicación sigue los principios de la arquitectura hexagonal (puertos y adaptadores):

```
src/main/java/com/ninja/dashboard/
├── domain/                 # Núcleo del negocio
│   ├── model/             # Entidades del dominio
│   ├── port/              # Interfaces (puertos)
│   └── service/           # Casos de uso
├── infrastructure/        # Adaptadores externos
│   ├── entity/           # Entidades JPA
│   ├── repository/       # Repositorios JPA
│   ├── adapter/          # Implementaciones de puertos
│   └── mapper/           # Mappers entidad-dominio
└── application/          # Capa de aplicación
    ├── controller/       # Controladores REST
    ├── dto/              # DTOs para API
    └── mapper/           # Mappers DTO-dominio
```

## Tecnologías

- **Spring Boot 3.2.0**
- **Java 17**
- **PostgreSQL**
- **Spring Data JPA**
- **Maven**

## Configuración

### Base de Datos

1. Crear una base de datos PostgreSQL
2. Configurar las variables de entorno o actualizar `application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/ninja_db
       username: ninja_user
       password: ninja_pass
   ```

3. Ejecutar el script SQL para crear las tablas:
   ```bash
   psql -h localhost -U ninja_user -d ninja_db -f src/main/resources/schema.sql
   ```

4. Cargar datos de ejemplo:
   ```bash
   psql -h localhost -U ninja_user -d ninja_db -f src/main/resources/data.sql
   ```

## Ejecución

```bash
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## API Endpoints

### Personajes
- `GET /api/personajes` - Obtener todos los personajes
- `GET /api/personajes/{id}` - Obtener personaje por ID
- `GET /api/personajes/top/{limite}` - Top personajes por power level
- `GET /api/personajes/aldea/{aldeaId}` - Personajes por aldea
- `POST /api/personajes` - Crear personaje
- `PUT /api/personajes/{id}` - Actualizar personaje
- `DELETE /api/personajes/{id}` - Eliminar personaje

### Aldeas
- `GET /api/aldeas` - Obtener todas las aldeas
- `GET /api/aldeas/{id}` - Obtener aldea por ID
- `POST /api/aldeas` - Crear aldea
- `PUT /api/aldeas/{id}` - Actualizar aldea
- `DELETE /api/aldeas/{id}` - Eliminar aldea

## Ejemplo de uso

### Crear un personaje
```bash
curl -X POST http://localhost:8080/api/personajes \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Hatake Kakashi",
    "alias": "Kakashi",
    "rango": "Jonin",
    "chakra": 85,
    "inteligencia": 95,
    "fuerza": 75,
    "velocidad": 90,
    "descripcion": "El ninja que copia"
  }'
```

### Obtener top 5 personajes
```bash
curl http://localhost:8080/api/personajes/top/5
```