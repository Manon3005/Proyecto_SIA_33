package com.model.domain;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String rut;
    private String nombre;
    private String apellido;
    private String correo;
    private List<Ticket> tickets; 
    private String contrasena;

    public Cliente(String rut, String contrasena, String nombre, String apellido, String correo) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.tickets = new ArrayList<>();
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
    
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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
        return "Cliente{" + "rut=" + rut + ", nombre=" + nombre + ", apellido=" + apellido + ", correo=" + correo + '}';
    }
}
