-- -------------------------------------------------------------
-- -------------------------------------------------------------
-- TablePlus 1.3.8
--
-- https://tableplus.com/
--
-- Database: adopcion_animales
-- Generation Time: 2025-12-12 02:59:04.318868
-- -------------------------------------------------------------

-- Save current session settings and set optimal values for import
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO';
SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0;
SET NAMES utf8mb4;

DROP DATABASE IF EXISTS adopcion_animales;
CREATE DATABASE adopcion_animales;
USE adopcion_animales;

CREATE TABLE `AdminAlbergue` (
  `id_admin` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(80) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `contraseña` varchar(60) NOT NULL,
  `albergue_id` int NOT NULL,
  PRIMARY KEY (`id_admin`),
  UNIQUE KEY `correo` (`correo`),
  UNIQUE KEY `albergue_id` (`albergue_id`),
  CONSTRAINT `AdminAlbergue_ibfk_1` FOREIGN KEY (`albergue_id`) REFERENCES `Albergue` (`id_albergue`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Adoptante` (
  `id_adoptante` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `telefono` varchar(12) NOT NULL,
  `direccion` varchar(200) NOT NULL,
  PRIMARY KEY (`id_adoptante`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Albergue` (
  `id_albergue` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `direccion` varchar(200) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `telefono` varchar(12) NOT NULL,
  PRIMARY KEY (`id_albergue`),
  UNIQUE KEY `correo` (`correo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Mascota` (
  `id_mascota` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `raza` varchar(50) DEFAULT NULL,
  `tamaño` enum('Pequeño','Mediano','Grande') NOT NULL,
  `peso` decimal(5,2) DEFAULT NULL,
  `especie` enum('Perro','Gato') NOT NULL,
  `sexo` enum('Macho','Hembra') NOT NULL,
  `descripcion` text,
  `esta_castrado` tinyint(1) DEFAULT '0',
  `esta_vacunado` tinyint(1) DEFAULT '0',
  `condicion_especial` varchar(200) DEFAULT NULL,
  `estado` enum('En albergue','En proceso de adopción','Adoptada') DEFAULT 'En albergue',
  `albergue_id` int NOT NULL,
  PRIMARY KEY (`id_mascota`),
  KEY `albergue_id` (`albergue_id`),
  CONSTRAINT `Mascota_ibfk_1` FOREIGN KEY (`albergue_id`) REFERENCES `Albergue` (`id_albergue`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `SolicitudAdopcion` (
  `id_solicitud` int NOT NULL AUTO_INCREMENT,
  `estado` enum('Pendiente','Aprobada','Rechazada','Cancelada') DEFAULT 'Pendiente',
  `fecha_solicitud` datetime DEFAULT CURRENT_TIMESTAMP,
  `fecha_respuesta` datetime DEFAULT NULL,
  `motivo_rechazo` text,
  `adoptante_id` int NOT NULL,
  `mascota_id` int NOT NULL,
  `fecha_cita` datetime DEFAULT NULL,
  PRIMARY KEY (`id_solicitud`),
  UNIQUE KEY `adoptante_id` (`adoptante_id`,`mascota_id`),
  KEY `mascota_id` (`mascota_id`),
  CONSTRAINT `SolicitudAdopcion_ibfk_1` FOREIGN KEY (`adoptante_id`) REFERENCES `Adoptante` (`id_adoptante`) ON DELETE CASCADE,
  CONSTRAINT `SolicitudAdopcion_ibfk_2` FOREIGN KEY (`mascota_id`) REFERENCES `Mascota` (`id_mascota`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `AdminAlbergue` (`id_admin`, `nombre`, `apellido`, `correo`, `contraseña`, `albergue_id`) VALUES 
(1, 'Juan', 'Pérez', 'juan.perez@albergue.com', '$2a$12$Qki7PV1vpkPmOAo9wGQogOjE79uArVtpJbWcjTiIaNEfWK/OKtat6', 1),
(2, 'María', 'Gómez', 'maria.gomez@albergue.com', '$2a$12$Qki7PV1vpkPmOAo9wGQogOjE79uArVtpJbWcjTiIaNEfWK/OKtat6', 2),
(3, 'Carlos', 'Rodríguez', 'carlos.rodriguez@albergue.com', '$2a$12$Qki7PV1vpkPmOAo9wGQogOjE79uArVtpJbWcjTiIaNEfWK/OKtat6', 3),
(4, 'Lucía', 'Fernández', 'lucia.fernandez@albergue.com', '$2a$12$Qki7PV1vpkPmOAo9wGQogOjE79uArVtpJbWcjTiIaNEfWK/OKtat6', 4);

INSERT INTO `Adoptante` (`id_adoptante`, `nombre`, `apellido`, `fecha_nacimiento`, `telefono`, `direccion`) VALUES 
(1, 'Luis', 'Martínez', '1990-05-12', '8091234567', 'Calle X #10, Santo Domingo'),
(2, 'Ana', 'Rodríguez', '1985-03-22', '8097654321', 'Calle Y #20, Santiago'),
(3, 'Pedro', 'González', '1992-07-30', '8095554444', 'Av. Z #15, Santo Domingo Oeste'),
(4, 'Mariela', 'Santos', '1988-11-18', '8097776666', 'Calle M #7, La Vega'),
(5, 'Sky', 'RobaPerro', '2006-10-18', '9094829381', 'Direccion falsa'),
(6, 'Sky', 'Andujar', '2006-12-20', '8094828193', 'Direccion falsa'),
(7, 'Bianca', 'Parra', '2006-04-28', '8094095912', 'Hora');

INSERT INTO `Albergue` (`id_albergue`, `nombre`, `direccion`, `correo`, `telefono`) VALUES 
(1, 'Albergue Amigos Peludos', 'Calle A #123, Santo Domingo', 'peludos@example.com', '8091234567'),
(2, 'Refugio Gatitos Callejeros', 'Av. B #45, Santiago', 'gatitos@example.com', '8097654322'),
(3, 'Huellitas Felices', 'Calle C #78, Santo Domingo Oeste', 'huellitas@example.com', '8091122334'),
(4, 'Patitas del Alma', 'Av. D #90, La Vega', 'patitas@example.com', '8099988776');

INSERT INTO `Mascota` (`id_mascota`, `nombre`, `fecha_nacimiento`, `raza`, `tamaño`, `peso`, `especie`, `sexo`, `descripcion`, `esta_castrado`, `esta_vacunado`, `condicion_especial`, `estado`, `albergue_id`) VALUES 
(1, 'Firulais', '2023-02-15', 'Labrador', 'Grande', 25.50, 'Perro', 'Macho', 'Muy amigable, le gusta correr.', 1, 1, NULL, 'En proceso de adopción', 1),
(2, 'Misu', '2024-01-10', 'Siames', 'Pequeño', 4.20, 'Gato', 'Hembra', 'Cariñosa, tranquila.', 0, 0, NULL, 'En proceso de adopción', 2),
(3, 'Rex', '2022-06-20', 'Pastor Alemán', 'Grande', 30.00, 'Perro', 'Macho', 'Protector y obediente.', 1, 1, NULL, 'En albergue', 1),
(4, 'Luna', '2023-09-05', 'Persa', 'Mediano', 5.10, 'Gato', 'Hembra', 'Tranquila y juguetona.', 1, 0, NULL, 'En albergue', 2),
(5, 'Max', '2023-03-12', 'Beagle', 'Mediano', 12.50, 'Perro', 'Macho', 'Muy activo, requiere ejercicio.', 0, 1, NULL, 'En albergue', 3),
(6, 'Nala', '2024-02-25', 'Maine Coon', 'Grande', 6.80, 'Gato', 'Hembra', 'Sociable con niños.', 0, 0, NULL, 'En albergue', 4);

INSERT INTO `SolicitudAdopcion` (`id_solicitud`, `estado`, `fecha_solicitud`, `fecha_respuesta`, `motivo_rechazo`, `adoptante_id`, `mascota_id`, `fecha_cita`) VALUES 
(6, 'Pendiente', '2025-12-11 00:00:00', NULL, NULL, 6, 1, '2025-12-12 14:00:00'),
(7, 'Pendiente', '2025-12-12 00:00:00', '2025-12-12 00:00:00', 'No implementado', 7, 2, '2025-12-13 14:30:00');


-- Restore original session settings
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
SET SQL_MODE=@OLD_SQL_MODE;
SET SQL_NOTES=@OLD_SQL_NOTES;
