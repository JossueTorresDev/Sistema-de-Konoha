-- Datos de ejemplo (seed)
-- Aldeas
INSERT INTO aldea (nombre, region, descripcion) VALUES
('Konohagakure', 'País del Fuego', 'Aldea oculta entre los árboles'),
('Amegakure', 'País de la Lluvia', 'Aldea de la lluvia');

-- Clanes
INSERT INTO clan (nombre, descripcion, id_aldea) VALUES
('Uchiha', 'Clan con kekkei genkai: Sharingan', (SELECT id FROM aldea WHERE nombre='Konohagakure')),
('Uzumaki', 'Clan con gran chakra y longevidad', (SELECT id FROM aldea WHERE nombre='Konohagakure'));

-- Jutsus
INSERT INTO jutsu (nombre, tipo, nivel, descripcion) VALUES
('Rasengan', 'Ninjutsu', 8, 'Esfera rotativa de chakra creada por compresión'),
('Chidori', 'Ninjutsu', 8, 'Técnica de concentración de chakra en la mano'),
('Sharingan', 'Kekkei Genkai', 9, 'Dōjutsu del clan Uchiha que permite percibir el chakra'),
('Amaterasu', 'Ninjutsu', 10, 'Llamas negras inextinguibles');

-- Personajes
INSERT INTO personaje (nombre, alias, rango, id_aldea, id_clan, nacimiento, chakra, inteligencia, fuerza, velocidad, descripcion, imagen_url) VALUES
('Uzumaki Naruto', 'Naruto', 'Kage', 
 (SELECT id FROM aldea WHERE nombre='Konohagakure'), 
 (SELECT id FROM clan WHERE nombre='Uzumaki'), 
 '1999-10-10', 95, 70, 80, 85, 'Hokage y protagonista', 'https://example.com/img/naruto.png'),
('Uchiha Sasuke', 'Sasuke', 'N/A', 
 (SELECT id FROM aldea WHERE nombre='Konohagakure'), 
 (SELECT id FROM clan WHERE nombre='Uchiha'), 
 '1999-07-23', 90, 95, 78, 88, 'Rival de Naruto', 'https://example.com/img/sasuke.png'),
('Uchiha Itachi', 'Itachi', 'N/A', 
 (SELECT id FROM aldea WHERE nombre='Konohagakure'), 
 (SELECT id FROM clan WHERE nombre='Uchiha'), 
 '1985-06-09', 88, 98, 60, 80, 'Genio prodigio del clan Uchiha', 'https://example.com/img/itachi.png');