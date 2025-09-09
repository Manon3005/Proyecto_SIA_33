package com.model.domain;

import java.util.ArrayList;
import java.util.List;

public class Empleado {
    private String rut;
    private String contrasena;
    private String nombre;
    private String apellido;
    private String correo;
    private List<Ticket> tickets;

    public Empleado(String rut, String contrasena, String nombre, String apellido, String correo) {
        this.rut = rut;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.tickets = new ArrayList<>();
    } 

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    
    public boolean agregarTicket(Ticket ticket) {
        for (Ticket t: tickets) {
            if (t.getId() == ticket.getId()) {
                return false;
            }
        }
        tickets.add(ticket);
        return true;
    }

    @Override
    public String toString() {
        return "Empleado{" + "rut=" + rut + ", nombre=" + nombre + ", apellido=" + apellido + ", correo=" + correo + '}';
    }
}
