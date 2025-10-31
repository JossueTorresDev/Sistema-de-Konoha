# Ninja Dashboard - Spring Boot MVC

Aplicación Spring Boot con arquitectura MVC tradicional para gestionar un dashboard de ninjas basado en el universo de Naruto.

## Arquitectura

La aplicación sigue el patrón MVC (Model-View-Controller) de Spring Boot:

```
src/main/java/com/ninja/dashboard/
├── model/                # Entidades JPA (Model)
├── repository/           # Repositorios Spring Data JPA
├── service/              # Servicios de negocio
├── controller/           # Controladores REST (Controller)
├── dto/                  # Data Transfer Objects
├── exception/            # Manejo de excepciones
└── config/               # Configuraciones
```

## Tecnologías

- **Spring Boot 3.2.0**
- **Java 17**
- **PostgreSQL** (Neon compatible)
- **Spring Data JPA**
- **Manejo de excepciones** personalizado
- **Maven**
- **UUID** como identificadores primarios

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

### Dashboard
- `GET /api/dashboard/stats` - Estadísticas generales del dashboard
- `GET /api/dashboard/top-personajes/{limite}` - Top personajes por power level
- `GET /api/dashboard/aldeas-stats` - Estadísticas de aldeas
- `GET /api/dashboard/jutsus-populares` - Jutsus más populares
- `GET /api/dashboard/resumen` - Resumen general

### Personajes
- `GET /api/personajes` - Obtener todos los personajes
- `GET /api/personajes/{id}` - Obtener personaje por ID
- `GET /api/personajes/{id}/jutsus` - Obtener personaje con sus jutsus
- `GET /api/personajes/top/{limite}` - Top personajes por power level
- `GET /api/personajes/aldea/{aldeaId}` - Personajes por aldea
- `GET /api/personajes/clan/{clanId}` - Personajes por clan
- `GET /api/personajes/rango/{rango}` - Personajes por rango
- `GET /api/personajes/search?nombre={nombre}` - Buscar personajes por nombre
- `GET /api/personajes/ranking?page={page}&size={size}` - Ranking paginado
- `POST /api/personajes` - Crear personaje
- `PUT /api/personajes/{id}` - Actualizar personaje
- `DELETE /api/personajes/{id}` - Eliminar personaje

### Aldeas
- `GET /api/aldeas` - Obtener todas las aldeas
- `GET /api/aldeas/{id}` - Obtener aldea por ID
- `GET /api/aldeas/{id}/personajes` - Obtener aldea con personajes
- `GET /api/aldeas/{id}/clanes` - Obtener aldea con clanes
- `GET /api/aldeas/nombre/{nombre}` - Obtener aldea por nombre
- `GET /api/aldeas/region/{region}` - Aldeas por región
- `GET /api/aldeas/stats` - Aldeas con conteo de personajes
- `POST /api/aldeas` - Crear aldea
- `PUT /api/aldeas/{id}` - Actualizar aldea
- `DELETE /api/aldeas/{id}` - Eliminar aldea

### Jutsus
- `GET /api/jutsus` - Obtener todos los jutsus
- `GET /api/jutsus/{id}` - Obtener jutsu por ID
- `GET /api/jutsus/nombre/{nombre}` - Obtener jutsu por nombre
- `GET /api/jutsus/tipo/{tipo}` - Jutsus por tipo
- `GET /api/jutsus/nivel/{nivel}` - Jutsus por nivel
- `GET /api/jutsus/nivel-minimo/{nivel}` - Jutsus con nivel mínimo
- `GET /api/jutsus/search?nombre={nombre}` - Buscar jutsus por nombre
- `GET /api/jutsus/personaje/{personajeId}` - Jutsus de un personaje
- `GET /api/jutsus/stats` - Jutsus con conteo de usuarios
- `GET /api/jutsus/tipos` - Tipos de jutsu disponibles
- `POST /api/jutsus` - Crear jutsu
- `PUT /api/jutsus/{id}` - Actualizar jutsu
- `DELETE /api/jutsus/{id}` - Eliminar jutsu

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