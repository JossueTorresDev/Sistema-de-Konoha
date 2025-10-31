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
    ('Uzumaki Naruto', 'Naruto', 'Kage', 
     (SELECT id FROM aldea WHERE nombre='Konohagakure'), 
     (SELECT id FROM clan WHERE nombre='Uzumaki'), 
     '1999-10-10', 95, 70, 80, 85, 'Hokage y protagonista', 
     'https://example.com/img/naruto.png'),
    ('Uchiha Sasuke', 'Sasuke', 'N/A', 
     (SELECT id FROM aldea WHERE nombre='Konohagakure'), 
     (SELECT id FROM clan WHERE nombre='Uchiha'), 
     '1999-07-23', 90, 95, 78, 88, 'Rival de Naruto', 
     'https://example.com/img/sasuke.png'),
    ('Uchiha Itachi', 'Itachi', 'N/A', 
     (SELECT id FROM aldea WHERE nombre='Konohagakure'), 
     (SELECT id FROM clan WHERE nombre='Uchiha'), 
     '1985-06-09', 88, 98, 60, 80, 'Genio prodigio del clan Uchiha', 
     'https://example.com/img/itachi.png');

-- Asignar jutsus a personajes (usa la función)
SELECT fn_asignar_jutsu(
    (SELECT id FROM personaje WHERE nombre='Uzumaki Naruto'), 
    (SELECT id FROM jutsu WHERE nombre='Rasengan'), 
    9
);

SELECT fn_asignar_jutsu(
    (SELECT id FROM personaje WHERE nombre='Uchiha Sasuke'), 
    (SELECT id FROM jutsu WHERE nombre='Chidori'), 
    9
);

SELECT fn_asignar_jutsu(
    (SELECT id FROM personaje WHERE nombre='Uchiha Sasuke'), 
    (SELECT id FROM jutsu WHERE nombre='Sharingan'), 
    10
);

SELECT fn_asignar_jutsu(
    (SELECT id FROM personaje WHERE nombre='Uchiha Itachi'), 
    (SELECT id FROM jutsu WHERE nombre='Sharingan'), 
    10
);

SELECT fn_asignar_jutsu(
    (SELECT id FROM personaje WHERE nombre='Uchiha Itachi'), 
    (SELECT id FROM jutsu WHERE nombre='Amaterasu'), 
    9
);

-- Misiones y participaciones
INSERT INTO mision (titulo, descripcion, dificultad, recompensa, fecha_inicio, fecha_fin, completada)
VALUES
    ('Protección del cartero', 'Protección de un cartero importante', 2, 100, '2024-01-10', '2024-01-12', TRUE),
    ('Recolección de información', 'Misión de reconocimiento en territorio enemigo', 6, 1000, '2024-03-01', NULL, FALSE);

INSERT INTO participacion_mision (id_mision, id_personaje, rol, exito)
VALUES
    ((SELECT id FROM mision WHERE titulo='Protección del cartero'), 
     (SELECT id FROM personaje WHERE nombre='Uzumaki Naruto'), 
     'Lider', TRUE),
    ((SELECT id FROM mision WHERE titulo='Recolección de información'), 
     (SELECT id FROM personaje WHERE nombre='Uchiha Sasuke'), 
     'Reconocimiento', FALSE);

-- Perfil shinobi (jsonb de ejemplo)
INSERT INTO perfil_shinobi (id_personaje, resumen)
VALUES
    ((SELECT id FROM personaje WHERE nombre='Uzumaki Naruto'),
     jsonb_build_object(
         'biografia', 'Hokage de Konoha, líder valiente',
         'habilidades', jsonb_build_array('Rasengan', 'Modo Sabio', 'Kurama'),
         'proyectos', jsonb_build_array(
             jsonb_build_object('nombre', 'Rescate de Konoha', 'year', 2024)
         )
     ));

-- Datos administrativos: crear usuario demo (hashed_password es placeholder)
INSERT INTO usuario (email, nombre, rol, hashed_password)
VALUES ('reclutador@example.com', 'Reclutador Demo', 'recruiter', 'changeme-hash');

-- Mantener materialized view actualizado tras semillas
SELECT refresh_mv_top_personajes();