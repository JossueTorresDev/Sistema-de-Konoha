-- schema_neon.sql
-- PostgreSQL schema para "Base de Datos Ninja / Dashboard"
-- Compatible con Neon (Postgres). Ejecutar con: psql -h <host> -U <user> -d <db> -f schema_neon.sql

-- 0) Extensiones útiles
CREATE EXTENSION IF NOT EXISTS pgcrypto;   -- gen_random_uuid()
CREATE EXTENSION IF NOT EXISTS citext;     -- case-insensitive text (opcional)

-- 1) Esquema base
SET search_path = public;

-- 2) Tablas maestras
CREATE TABLE aldea (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre TEXT NOT NULL UNIQUE,
    region TEXT,
    descripcion TEXT,
    creado_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE clan (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre TEXT NOT NULL UNIQUE,
    descripcion TEXT,
    id_aldea UUID REFERENCES aldea(id) ON DELETE SET NULL,
    creado_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE jutsu (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre TEXT NOT NULL UNIQUE,
    tipo TEXT, -- e.g., Ninjutsu, Genjutsu, Taijutsu, Kekkei Genkai
    nivel INTEGER DEFAULT 1, -- 1..10 (indicativo)
    descripcion TEXT,
    creado_at TIMESTAMPTZ DEFAULT now()
);

-- 3) Personajes (shinobis)
CREATE TABLE personaje (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre TEXT NOT NULL,
    alias TEXT,
    rango TEXT, -- e.g., Genin, Chunin, Jonin, Kage
    id_aldea UUID REFERENCES aldea(id) ON DELETE SET NULL,
    id_clan UUID REFERENCES clan(id) ON DELETE SET NULL,
    nacimiento DATE,
    chakra INTEGER DEFAULT 50,        -- valor base
    inteligencia INTEGER DEFAULT 50,  -- stats ejemplo
    fuerza INTEGER DEFAULT 50,
    velocidad INTEGER DEFAULT 50,
    descripcion TEXT,
    imagen_url TEXT,
    creado_at TIMESTAMPTZ DEFAULT now(),
    UNIQUE (nombre, alias)
);

-- 4) Relación N:M: personaje <-> jutsu
CREATE TABLE personaje_jutsu (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_personaje UUID REFERENCES personaje(id) ON DELETE CASCADE,
    id_jutsu UUID REFERENCES jutsu(id) ON DELETE CASCADE,
    dominio INTEGER DEFAULT 1, -- 1..10 dominio del jutsu por ese personaje
    aprendido_en DATE,
    UNIQUE (id_personaje, id_jutsu)
);

-- 5) Misiones y participaciones
CREATE TABLE mision (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    titulo TEXT NOT NULL,
    descripcion TEXT,
    dificultad INTEGER DEFAULT 1, -- 1..10
    recompensa INTEGER DEFAULT 0,
    fecha_inicio DATE,
    fecha_fin DATE,
    completada BOOLEAN DEFAULT FALSE,
    creado_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE participacion_mision (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_mision UUID REFERENCES mision(id) ON DELETE CASCADE,
    id_personaje UUID REFERENCES personaje(id) ON DELETE CASCADE,
    rol TEXT, -- ej. Lider, Apoyo, Reconocimiento
    exito BOOLEAN,
    notas TEXT,
    creado_at TIMESTAMPTZ DEFAULT now(),
    UNIQUE (id_mision, id_personaje)
);

-- 6) Usuarios (para contactos / login demos)
CREATE TABLE usuario (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email citext NOT NULL UNIQUE,
    nombre TEXT,
    rol TEXT DEFAULT 'viewer', -- viewer, admin, recruiter, dev
    created_at TIMESTAMPTZ DEFAULT now(),
    hashed_password TEXT -- placeholder (no hashing logic aquí)
);

-- 7) Perfil extendido del shinobi (jsonb para flexibilidad)
CREATE TABLE perfil_shinobi (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_personaje UUID UNIQUE REFERENCES personaje(id) ON DELETE CASCADE,
    resumen JSONB,       -- campo flexible para UI (skills, historial, logros)
    estado_publico BOOLEAN DEFAULT TRUE,
    actualizado_at TIMESTAMPTZ DEFAULT now()
);

-- 8) Índices útiles
CREATE INDEX idx_personaje_nombre ON personaje (lower(nombre));
CREATE INDEX idx_personaje_aldea ON personaje (id_aldea);
CREATE INDEX idx_personaje_clan ON personaje (id_clan);
CREATE INDEX idx_jutsu_tipo ON jutsu (tipo);
CREATE INDEX idx_mision_fecha ON mision (fecha_inicio, fecha_fin);

-- 9) Vistas y materialized views para dashboard
-- Vista simple de personaje con estadísticas "power_level"
CREATE VIEW vw_personaje_power AS
SELECT
  p.id,
  p.nombre,
  p.alias,
  p.rango,
  p.id_aldea,
  p.id_clan,
  p.imagen_url,
  (p.chakra * 0.4 + p.inteligencia * 0.25 + p.fuerza * 0.2 + p.velocidad * 0.15) AS power_level
FROM personaje p;

-- Materialized view: top 10 personajes por power_level
CREATE MATERIALIZED VIEW mv_top_personajes AS
SELECT * FROM vw_personaje_power
ORDER BY power_level DESC
LIMIT 10;

-- Refresh helper (se puede programar en cron/external job)
CREATE OR REPLACE FUNCTION refresh_mv_top_personajes()
RETURNS void LANGUAGE plpgsql AS $$
BEGIN
  REFRESH MATERIALIZED VIEW CONCURRENTLY mv_top_personajes;
EXCEPTION WHEN others THEN
  -- si falla el concurrent refresh (tiny DBs), fallback
  REFRESH MATERIALIZED VIEW mv_top_personajes;
END;
$$;

-- 10) Funciones útiles
-- Función para añadir jutsu a un personaje (evita duplicados)
CREATE OR REPLACE FUNCTION fn_asignar_jutsu(_personaje UUID, _jutsu UUID, _dominio INTEGER DEFAULT 1)
RETURNS VOID LANGUAGE plpgsql AS $$
BEGIN
  INSERT INTO personaje_jutsu (id_personaje, id_jutsu, dominio, aprendido_en)
  VALUES (_personaje, _jutsu, _dominio, current_date)
  ON CONFLICT (id_personaje, id_jutsu) DO UPDATE
    SET dominio = GREATEST(personaje_jutsu.dominio, EXCLUDED.dominio);
END;
$$;

-- Función para calcular "score" de un personaje (retorna numeric)
CREATE OR REPLACE FUNCTION fn_calcular_score_personaje(_id UUID)
RETURNS numeric LANGUAGE sql AS $$
  SELECT (p.chakra * 0.4 + p.inteligencia * 0.25 + p.fuerza * 0.2 + p.velocidad * 0.15)
  FROM personaje p WHERE p.id = _id;
$$;

-- 11) Datos de ejemplo (seed)
-- Aldeas
INSERT INTO aldea (nombre, region, descripcion)
VALUES
  ('Konohagakure', 'País del Fuego', 'Aldea oculta entre los árboles'),
  ('Amegakure', 'País de la Lluvia', 'Aldea de la lluvia');

-- Clanes
INSERT INTO clan (nombre, descripcion, id_aldea)
VALUES
  ('Uchiha', 'Clan con kekkei genkai: Sharingan', (SELECT id FROM aldea WHERE nombre='Konohagakure')),
  ('Uzumaki', 'Clan con gran chakra y longevidad', (SELECT id FROM aldea WHERE nombre='Konohagakure'));

-- Jutsus
INSERT INTO jutsu (nombre, tipo, nivel, descripcion)
VALUES
  ('Rasengan', 'Ninjutsu', 8, 'Esfera rotativa de chakra creada por compresión'),
  ('Chidori', 'Ninjutsu', 8, 'Técnica de concentración de chakra en la mano'),
  ('Sharingan', 'Kekkei Genkai', 9, 'Dōjutsu del clan Uchiha que permite percibir el chakra'),
  ('Amaterasu', 'Ninjutsu', 10, 'Llamas negras inextinguibles');

-- Personajes
INSERT INTO personaje (nombre, alias, rango, id_aldea, id_clan, nacimiento, chakra, inteligencia, fuerza, velocidad, descripcion, imagen_url)
VALUES
  ('Uzumaki Naruto', 'Naruto', 'Kage', (SELECT id FROM aldea WHERE nombre='Konohagakure'), (SELECT id FROM clan WHERE nombre='Uzumaki'), '1999-10-10', 95, 70, 80, 85, 'Hokage y protagonista', 'https://example.com/img/naruto.png'),
  ('Uchiha Sasuke', 'Sasuke', 'N/A', (SELECT id FROM aldea WHERE nombre='Konohagakure'), (SELECT id FROM clan WHERE nombre='Uchiha'), '1999-07-23', 90, 95, 78, 88, 'Rival de Naruto', 'https://example.com/img/sasuke.png'),
  ('Uchiha Itachi', 'Itachi', 'N/A', (SELECT id FROM aldea WHERE nombre='Konohagakure'), (SELECT id FROM clan WHERE nombre='Uchiha'), '1985-06-09', 88, 98, 60, 80, 'Genio prodigio del clan Uchiha', 'https://example.com/img/itachi.png');

-- Asignar jutsus a personajes (usa la función)
SELECT fn_asignar_jutsu((SELECT id FROM personaje WHERE nombre='Uzumaki Naruto'), (SELECT id FROM jutsu WHERE nombre='Rasengan'), 9);
SELECT fn_asignar_jutsu((SELECT id FROM personaje WHERE nombre='Uchiha Sasuke'), (SELECT id FROM jutsu WHERE nombre='Chidori'), 9);
SELECT fn_asignar_jutsu((SELECT id FROM personaje WHERE nombre='Uchiha Sasuke'), (SELECT id FROM jutsu WHERE nombre='Sharingan'), 10);
SELECT fn_asignar_jutsu((SELECT id FROM personaje WHERE nombre='Uchiha Itachi'), (SELECT id FROM jutsu WHERE nombre='Sharingan'), 10);
SELECT fn_asignar_jutsu((SELECT id FROM personaje WHERE nombre='Uchiha Itachi'), (SELECT id FROM jutsu WHERE nombre='Amaterasu'), 9);

-- Misiones y participaciones
INSERT INTO mision (titulo, descripcion, dificultad, recompensa, fecha_inicio, fecha_fin, completada)
VALUES
  ('Protección del cartero', 'Protección de un cartero importante', 2, 100, '2024-01-10', '2024-01-12', TRUE),
  ('Recolección de información', 'Misión de reconocimiento en territorio enemigo', 6, 1000, '2024-03-01', NULL, FALSE);

INSERT INTO participacion_mision (id_mision, id_personaje, rol, exito)
VALUES
  ((SELECT id FROM mision WHERE titulo='Protección del cartero'), (SELECT id FROM personaje WHERE nombre='Uzumaki Naruto'), 'Lider', TRUE),
  ((SELECT id FROM mision WHERE titulo='Recolección de información'), (SELECT id FROM personaje WHERE nombre='Uchiha Sasuke'), 'Reconocimiento', FALSE);

-- Perfil shinobi (jsonb de ejemplo)
INSERT INTO perfil_shinobi (id_personaje, resumen)
VALUES
  ((SELECT id FROM personaje WHERE nombre='Uzumaki Naruto'),
   jsonb_build_object(
     'biografia','Hokage de Konoha, líder valiente',
     'habilidades', jsonb_build_array('Rasengan','Modo Sabio','Kurama'),
     'proyectos', jsonb_build_array(jsonb_build_object('nombre','Rescate de Konoha','year',2024))
   )
  );

-- 12) Queries de ejemplo para tu dashboard / API
-- Lista de personajes con power_level
-- SELECT * FROM vw_personaje_power ORDER BY power_level DESC LIMIT 20;

-- Personas por clan
-- SELECT c.nombre as clan, count(p.*) as miembros
-- FROM clan c LEFT JOIN personaje p ON p.id_clan = c.id
-- GROUP BY c.nombre ORDER BY miembros DESC;

-- Top jutsus más usados (por número de personajes que lo conocen)
-- SELECT j.nombre, count(pj.*) as usuarios
-- FROM jutsu j
-- JOIN personaje_jutsu pj ON pj.id_jutsu = j.id
-- GROUP BY j.nombre
-- ORDER BY usuarios DESC;

-- 13) Datos administrativos: crear usuario demo (hashed_password es placeholder)
INSERT INTO usuario (email, nombre, rol, hashed_password)
VALUES ('reclutador@example.com', 'Reclutador Demo', 'recruiter', 'changeme-hash');

-- 14) Mantener materialized view actualizado tras semillas
-- Nota: si la DB es pequeña y no soporta CONCURRENTLY al crear la mv, el refresh funcionará sin concurrent.
SELECT refresh_mv_top_personajes();

-- FIN del script
