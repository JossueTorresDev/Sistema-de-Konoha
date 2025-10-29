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