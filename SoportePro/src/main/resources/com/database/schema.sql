CREATE DATABASE IF NOT EXISTS soporte_pro;
USE soporte_pro;

CREATE TABLE cliente (
    rut VARCHAR(15) PRIMARY KEY,
    contrasena VARCHAR(15) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    correo VARCHAR(100) NOT NULL
);

CREATE TABLE empleado (
    rut VARCHAR(15) PRIMARY KEY,
    contrasena VARCHAR(15) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    correo VARCHAR(100) NOT NULL
);

CREATE TABLE ticket (
    id INT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descripcion TEXT,
    estado ENUM('PENDIENTE', 'EN_PROCESO', 'TRATADO', 'EN_EVALUACION', 'CANCELADO') DEFAULT 'PENDIENTE',
    satisfaccion INT CHECK (satisfaccion BETWEEN 1 AND 5 OR satisfaccion IS NULL),
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_finalizacion DATETIME NULL,

    cliente_rut VARCHAR(15) NOT NULL,
    empleado_rut VARCHAR(15),
    FOREIGN KEY (cliente_rut) REFERENCES cliente(rut) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
    FOREIGN KEY (empleado_rut) REFERENCES empleado(rut) 
        ON DELETE SET NULL 
        ON UPDATE CASCADE
);

INSERT INTO cliente (rut, contrasena, nombre, apellido, correo) VALUES
('11111111-1', 'pass', 'Juan', 'Perez', 'juan.perez@mail.com'),
('22222222-2', 'pass', 'María', 'Gonzalez', 'maria.gonzalez@mail.com'),
('33333333-3', 'pass', 'Luiz', 'Ramirez', 'luiz.ramirez@mail.com');

INSERT INTO empleado (rut, contrasena, nombre, apellido, correo) VALUES
('44444444-4', 'pass', 'Ana', 'Lopez', 'ana.lopez@mail.com'),
('55555555-5', 'pass', 'Pedro', 'Sanchez', 'pedro.sanchez@mail.com');

INSERT INTO ticket (id, titulo, descripcion, estado, satisfaccion, cliente_rut, empleado_rut) VALUES
(0, 'Problema Internet', 'No tiene conexion en su domicilio.', 'EN_PROCESO', NULL, '11111111-1', '44444444-4'),
(1, 'Error Factura', 'Monto incorrecto en la ultima boleta.', 'EN_PROCESO', NULL, '11111111-1', '55555555-5'),
(2, 'Cambio Plan', 'Desea cambiar a un plan mas economico.', 'PENDIENTE', NULL, '22222222-2', NULL);