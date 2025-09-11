package com.model.domain;

public class Empleado extends Usuario {

    public Empleado(String rut, String contrasena, String nombre, String apellido, String correo) {
        super(rut, contrasena, nombre, apellido, correo);
    } 

    @Override
    public String toString() {
        return "Empleado{" + "rut=" + rut + ", nombre=" + nombre + ", apellido=" + apellido + ", correo=" + correo + '}';
    }
}
