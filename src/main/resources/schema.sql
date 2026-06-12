-- Eliminar tablas si existen
DROP TABLE IF EXISTS rol;
DROP TABLE IF EXISTS usuario;

-- Crear tabla usuario
CREATE TABLE usuario (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL
);

-- Crear tabla rol con foreign key a usuario
CREATE TABLE rol (
    id_rol BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    id_usuario BIGINT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- Insertar usuario admin con contraseña "123" encriptada
INSERT INTO usuario (username, password) VALUES 
('admin', '$2a$10$WJ2/oYOJ.tmN3qJjipMcTujLjllQD79/FsXNIdYAJdJ8VUPgHMvUO');

-- Insertar roles para admin (IMPORTANTE: usar ROLE_ prefix)
INSERT INTO rol (nombre, id_usuario) VALUES 
('ROLE_ADMIN', 1),
('ROLE_USER', 1);

-- Verificar datos
SELECT u.id_usuario, u.username, r.nombre as rol 
FROM usuario u 
INNER JOIN rol r ON u.id_usuario = r.id_usuario;
