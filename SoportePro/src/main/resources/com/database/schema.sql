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
    correo VARCHAR(100) NOT NULL,
    salario DOUBLE 
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
('33333333-3', 'pass', 'Luiz', 'Ramirez', 'luiz.ramirez@mail.com'),
('7168889-5' , 'pass',  'Jose' , 'Marquez', 'jose.marquez@mail.com'),
('15207325-9' ,'pass',  'Marcos' , 'Montiel', 'marcos.montiel.a@mail.com'),
('24740451-1' ,'pass',  'Carla' , 'Sagardia', 'carla.sagardia.a@mail.com');

INSERT INTO empleado (rut, contrasena, nombre, apellido, correo, salario) VALUES
('44444444-4', 'pass', 'Ana', 'Lopez', 'ana.lopez@mail.com',230000.0),
('55555555-5', 'pass', 'Pedro', 'Sanchez', 'pedro.sanchez@mail.com',340000.0),
('23089856-1', 'pass', 'Julian', 'Sambrano', 'julian.sambrano@mail.com',320000.0),
('24269437-6', 'pass', 'Keren', 'Vasquez', 'keren.vasquez@mail.com',490000.0);




INSERT INTO ticket (id, titulo, descripcion, estado, satisfaccion, cliente_rut, empleado_rut) VALUES
(0, 'Problema Internet', 'No tiene conexion en su domicilio.', 'EN_PROCESO', NULL, '11111111-1', '44444444-4'),
(1, 'Error Factura', 'Monto incorrecto en la ultima boleta.', 'EN_PROCESO', NULL, '11111111-1', '55555555-5'),
(2, 'Cambio Plan', 'Desea cambiar a un plan mas economico.', 'EN_PROCESO', NULL, '22222222-2', '23089856-1'),
(3, 'Problema Internet', 'No tiene conexion en su domicilio.', 'PENDIENTE', NULL, '7168889-5', NULL),
(4, 'Cambio Plan', 'Desea cambiar a un plan con mas beneficios .', 'PENDIENTE', NULL, '15207325-9', NULL),
(5, 'Error Factura', 'Monto incorrecto en la ultima boleta.', 'EN_PROCESO', NULL, '24740451-1', '24269437-6');
