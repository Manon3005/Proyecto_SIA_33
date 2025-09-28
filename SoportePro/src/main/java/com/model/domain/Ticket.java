package com.model.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Ticket {
    private int id;
    private String titulo;
    private String descripcion;
    private EstadoTicket estado;
    private int satisfaccion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaFinalizacion;
    private String clienteRut;
    private String empleadoRut;

    public Ticket(String titulo, String descripcion, String clienteRut) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = LocalDateTime.now(ZoneId.of("America/Santiago"));
        this.clienteRut = clienteRut;
        this.estado = EstadoTicket.PENDIENTE;
        this.satisfaccion = 0;
        this.fechaFinalizacion = null;
        this.empleadoRut = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoTicket getEstado() {
        return estado;
    }

    public void setEstado(EstadoTicket estado) {
        this.estado = estado;
    }

    public int getSatisfaccion() {
        return satisfaccion;
    }

    public void setSatisfaccion(int satisfaccion) {
        this.satisfaccion = satisfaccion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(LocalDateTime fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public String getClienteRut() {
        return clienteRut;
    }

    public void setClienteRut(String clienteRut) {
        this.clienteRut = clienteRut;
    }

    public String getEmpleadoRut() {
        return empleadoRut;
    }

    public void setEmpleadoRut(String empleadoRut) {
        this.empleadoRut = empleadoRut;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
