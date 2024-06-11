-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 04-04-2024 a las 06:04:52
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `clinica`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `administradores`
--

CREATE TABLE `administradores` (
  `id` int(10) NOT NULL,
  `usuario` varchar(20) NOT NULL,
  `clave` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `administradores`
--

INSERT INTO `administradores` (`id`, `usuario`, `clave`) VALUES
(1, 'admin', '1234');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cargos`
--

CREATE TABLE `cargos` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cargos`
--

INSERT INTO `cargos` (`id`, `descripcion`) VALUES
(1, 'Secretario/a'),
(2, 'Limpieza'),
(3, 'Seguridad'),
(4, 'Camillero/a'),
(5, 'Cocinero/a'),
(6, 'Enfermero/a'),
(7, 'Medico/a'),
(8, 'Gerente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `especialidades`
--

CREATE TABLE `especialidades` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `especialidades`
--

INSERT INTO `especialidades` (`id`, `descripcion`) VALUES
(1, 'CARDIOLOGIA'),
(2, 'RADIOLOGIA'),
(3, 'PEDIATRIA'),
(4, 'ODONTOLOGIA'),
(5, 'OFTALMOLOGIA'),
(6, 'OTORRINOLARINGOLOGIA'),
(7, 'DERMATOLOGIA'),
(9, 'GINECOLOGIA'),
(11, 'GERIATRIA'),
(12, 'MEDICINA CLINICA'),
(13, 'KINESIOLOGIA'),
(14, 'GASTROENTEROLOGIA');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `horarios`
--

CREATE TABLE `horarios` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  `orden` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `horarios`
--

INSERT INTO `horarios` (`id`, `descripcion`, `orden`) VALUES
(1, ' 12hs a 15hs', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `obras_sociales`
--

CREATE TABLE `obras_sociales` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `obras_sociales`
--

INSERT INTO `obras_sociales` (`id`, `descripcion`) VALUES
(1, 'INSSEP'),
(2, 'OSPEDIC'),
(3, 'AVALIAN'),
(4, 'AMFFA SALUD'),
(5, 'ASOCIART'),
(6, 'MEDIFE'),
(7, 'OSECAC'),
(8, 'PAMI');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pacientes`
--

CREATE TABLE `pacientes` (
  `id` int(11) NOT NULL,
  `apellido` varchar(20) DEFAULT NULL,
  `nombre` varchar(40) DEFAULT NULL,
  `domicilio` varchar(60) DEFAULT NULL,
  `dni_paciente` varchar(10) DEFAULT NULL,
  `localidad` varchar(60) DEFAULT NULL,
  `provincia` varchar(45) DEFAULT NULL,
  `celulares` varchar(50) DEFAULT NULL,
  `telefonos` varchar(50) DEFAULT NULL,
  `emails` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `pacientes`
--

INSERT INTO `pacientes` (`id`, `apellido`, `nombre`, `domicilio`, `dni_paciente`, `localidad`, `provincia`, `celulares`, `telefonos`, `emails`) VALUES
(1, 'OBREGON', 'CESAR', 'BARRIO 713', '44331143', 'SAENZ PEÑA', 'CHACO', '3644524941', '0', 'CESAROBREGON66@GMAIL.COM'),
(4, 'OBREGON', 'MARIO', 'BARRIO 713 MZ. 104 PC. 14', '20384463', 'PCIA. ROQUE SAENZ PEÑA', 'CHACO', '3644650027', '0', 'MARIOCESAROBREGON@GMAIL.COM'),
(5, 'GONZALEZ', 'JOSE', 'CALLE 27 ENTRE 2 Y 4', '20394557', 'SAENZ PEÑA', 'CHACO', '3644853675', '0', 'JOSEGLEZ@GMAIL.COM'),
(6, 'MARTINEZ', 'LUCIANO ANDRES', 'BARRIO ENSANSHE SUR', '40229854', 'SAENZ PEÑA', 'CHACO', '3644854765', '-', 'LUCIANOMARTINEZ@GMAIL.COM'),
(7, 'Fabian Andres', 'Fabian Andres', 'Chaco', '41374567', 'Saenz Peña', 'Chaco', '-', '-', 'fabianmendez@gmail.com'),
(8, 'Muñoz', 'Hernan', 'Chaco', '36784567', 'Saenz Peña', 'Chaco', '-', '-', '-'),
(9, 'Florez', 'Camila Luz', 'Chaco', '44735674', 'Saenz Peña', 'Chaco', '-', '-', 'camiflo@gmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personales`
--

CREATE TABLE `personales` (
  `id` int(11) NOT NULL,
  `usuario` varchar(20) NOT NULL,
  `clave` varchar(20) DEFAULT NULL,
  `apellido` varchar(20) NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `cargo_id` int(11) NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_fin` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `personales`
--

INSERT INTO `personales` (`id`, `usuario`, `clave`, `apellido`, `nombre`, `cargo_id`, `fecha_inicio`, `fecha_fin`) VALUES
(1, 'fabriciog', '1234', 'Gonzalez', 'Fabricio', 1, '2024-01-01', '2024-12-31'),
(2, 'federicom', '1234', 'Martinez', 'Federico', 3, '2022-01-01', '2024-06-30');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesionales`
--

CREATE TABLE `profesionales` (
  `id` int(11) NOT NULL,
  `usuario` varchar(20) NOT NULL,
  `clave` varchar(20) DEFAULT NULL,
  `apellido` varchar(20) NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `matricula` int(11) NOT NULL,
  `titulo_profesional` varchar(40) NOT NULL,
  `email` varchar(40) DEFAULT NULL,
  `celular` varchar(20) DEFAULT NULL,
  `foto` varchar(50) DEFAULT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `profesionales`
--

INSERT INTO `profesionales` (`id`, `usuario`, `clave`, `apellido`, `nombre`, `matricula`, `titulo_profesional`, `email`, `celular`, `foto`, `estado`) VALUES
(1, 'maurog', '1234', 'Gonzalez', 'Mauro', 43535, 'Medico', 'maurogzlz@gmail.com', '3644524573', '', 1),
(2, 'maurof', '1234', 'Fernandez', 'Mauro', 324233, 'Medico', 'maurofdez@gmail.com', '3644734527', '2.jpg', 1),
(3, 'LEANDROM', '12345', 'MARTINEZ', 'LEANDRO', 345343, 'MEDICO', 'LEAMARTINEZ@GMAIL.COM', '3644598227', '3644598227', 1),
(4, 'MGOMEZ', '1234', 'GOMEZ', 'MARIA', 64553454, 'PEDIATRA', 'MARIAGOMEZ@GMAIL.COM', '3644834618', '-', 1),
(5, 'JVILLA', '1234', 'VILLALBA', 'JOSE', 6454565, 'GINECOLOGO', 'JVILLA@GMAIL.COM', '3644962784', '-', 1),
(6, 'LAUFER', '1234', 'FERNANDEZ', 'LAURA', 34533454, 'CARDIOLOGIA', 'LAURAFDEZ@GMAIL.COM', '3644953765', '-', 1),
(7, 'CAMIDOM', '1234', 'DOMINGUEZ', 'CAMILA', 34543540, 'KINESIOLOGIA', 'CAMIDOMINGUEZ@GMAIL.COM', '3644596826', '-', 1),
(8, 'ALVAREZM', '1234', 'ALVAREZ', 'MARTIN', 34534345, 'KINESIOLOGIA', 'MARTINALVAREZ@GMAIL.COM', '3644293653', '-', 1),
(9, 'FECHEV', '1234', 'ECHEVERRIA', 'FERNANDO', 43534534, 'GASTROENTEROLOGO', 'FERNANDOECHEV@GMAIL.COM', '3644853764', '-', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesionales_especialidades`
--

CREATE TABLE `profesionales_especialidades` (
  `id` int(11) NOT NULL,
  `profesional_id` int(11) NOT NULL,
  `especialidad_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `profesionales_especialidades`
--

INSERT INTO `profesionales_especialidades` (`id`, `profesional_id`, `especialidad_id`) VALUES
(2, 1, 1),
(3, 1, 12);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `turnos`
--

CREATE TABLE `turnos` (
  `id` int(11) NOT NULL,
  `profesional_id` int(11) NOT NULL,
  `especialidad_id` int(11) NOT NULL,
  `fecha` datetime NOT NULL,
  `apellido_paciente` varchar(20) NOT NULL,
  `nombre_paciente` varchar(40) NOT NULL,
  `dni_paciente` varchar(10) NOT NULL,
  `obra_social` int(11) DEFAULT NULL,
  `nro_credencial` varchar(10) DEFAULT NULL,
  `atencion_particular` tinyint(1) DEFAULT NULL,
  `motivo_turno` text NOT NULL,
  `estado` smallint(6) NOT NULL,
  `diagnostico` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `turnos`
--

INSERT INTO `turnos` (`id`, `profesional_id`, `especialidad_id`, `fecha`, `apellido_paciente`, `nombre_paciente`, `dni_paciente`, `obra_social`, `nro_credencial`, `atencion_particular`, `motivo_turno`, `estado`, `diagnostico`) VALUES
(2, 1, 1, '2024-03-12 00:00:00', 'GONZALEZ', 'JOSE', '20394557', 2, '4534534545', 0, 'dolor muscular', 1, '--'),
(5, 2, 4, '2024-03-21 00:00:00', 'GONZALEZ', 'JOSE', '20394557', 3, '4353434', 0, 'dolor de muela', 1, '-'),
(6, 2, 6, '2024-03-18 00:00:00', 'OBREGON', 'MARIO', '20384463', 2, '4443355544', 0, '-', 1, '-'),
(7, 7, 11, '2024-03-28 00:00:00', 'MARTINEZ', 'LUCIANO ANDRES', '40229854', 0, '657565', 1, '-', 1, '-'),
(8, 9, 5, '2024-03-31 00:00:00', 'GONZALEZ', 'JOSE', '20394557', 0, '435345345', 1, '-', 1, '-');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `administradores`
--
ALTER TABLE `administradores`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `cargos`
--
ALTER TABLE `cargos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `especialidades`
--
ALTER TABLE `especialidades`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `horarios`
--
ALTER TABLE `horarios`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `obras_sociales`
--
ALTER TABLE `obras_sociales`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `pacientes`
--
ALTER TABLE `pacientes`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `personales`
--
ALTER TABLE `personales`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cargo_id` (`cargo_id`);

--
-- Indices de la tabla `profesionales`
--
ALTER TABLE `profesionales`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `profesionales_especialidades`
--
ALTER TABLE `profesionales_especialidades`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_especialidad` (`especialidad_id`),
  ADD KEY `fk_profesional` (`profesional_id`);

--
-- Indices de la tabla `turnos`
--
ALTER TABLE `turnos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_profesional_turnos` (`profesional_id`),
  ADD KEY `fk_especialidad_turnos` (`especialidad_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `administradores`
--
ALTER TABLE `administradores`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `cargos`
--
ALTER TABLE `cargos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `especialidades`
--
ALTER TABLE `especialidades`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `horarios`
--
ALTER TABLE `horarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `obras_sociales`
--
ALTER TABLE `obras_sociales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `pacientes`
--
ALTER TABLE `pacientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `personales`
--
ALTER TABLE `personales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `profesionales`
--
ALTER TABLE `profesionales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `profesionales_especialidades`
--
ALTER TABLE `profesionales_especialidades`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `turnos`
--
ALTER TABLE `turnos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `personales`
--
ALTER TABLE `personales`
  ADD CONSTRAINT `personales_ibfk_1` FOREIGN KEY (`cargo_id`) REFERENCES `cargos` (`id`);

--
-- Filtros para la tabla `profesionales_especialidades`
--
ALTER TABLE `profesionales_especialidades`
  ADD CONSTRAINT `fk_especialidad` FOREIGN KEY (`especialidad_id`) REFERENCES `especialidades` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_profesional` FOREIGN KEY (`profesional_id`) REFERENCES `profesionales` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Filtros para la tabla `turnos`
--
ALTER TABLE `turnos`
  ADD CONSTRAINT `fk_especialidad_turnos` FOREIGN KEY (`especialidad_id`) REFERENCES `especialidades` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_profesional_turnos` FOREIGN KEY (`profesional_id`) REFERENCES `profesionales` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
