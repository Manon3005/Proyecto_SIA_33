/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mi.proyecto.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *
 * @author manon
 */
public class Ticket {
    private int id;
    private String titulo;
    private String descripcion;
    private EstadoTicket estado;
    private int satisfaccion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaFinalizacion;
    private Cliente cliente;
    private Empleado empleado;

    public Ticket(String titulo, String descripcion, Cliente cliente) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = LocalDateTime.now(ZoneId.of("America/Santiago"));
        this.cliente = cliente;
        this.estado = EstadoTicket.PENDIENTE;
        this.satisfaccion = 0;
        this.fechaFinalizacion = null;
        this.empleado = null;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public String toString() {
        return "Ticket{" + "id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", estado=" + estado + ", satisfaccion=" + satisfaccion + ", fechaCreacion=" + fechaCreacion + ", fechaFinalizacion=" + fechaFinalizacion + '}';
    }
}
