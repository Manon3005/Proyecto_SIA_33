package com.model.domain;

public class Cliente extends Usuario {
    
    public Cliente(String rut, String contrasena, String nombre, String apellido, String correo) {
        super(rut, contrasena, nombre, apellido, correo);
    }

    @Override
    public String toString() {
        for (Ticket ticket: tickets) {
            System.out.println("Ticket: " + ticket.getTitulo());
        }
        return "Cliente{" + "rut=" + rut + ", nombre=" + nombre + ", apellido=" + apellido + ", correo=" + correo + '}';
    }
}
