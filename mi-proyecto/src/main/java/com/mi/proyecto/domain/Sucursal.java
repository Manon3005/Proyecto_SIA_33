/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mi.proyecto.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author manon
 */
public class Sucursal {
    private int id;
    private String nombre;
    private String ubicación;
    private List<Cliente> clientes;
    private List<Empleado> empleados;

    public Sucursal(int id, String nombre, String ubicación) {
        this.id = id;
        this.nombre = nombre;
        this.ubicación = ubicación;
        clientes = new ArrayList<>();
        empleados = new ArrayList<>();
    }
     
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicación() {
        return ubicación;
    }

    public void setUbicación(String ubicación) {
        this.ubicación = ubicación;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
    
    public boolean agregarCliente(Cliente cliente) {
        for (Cliente c: clientes) {
            if (c.getRut().equals(cliente.getRut())) {
                return false;
            }
        }
        clientes.add(cliente);
        return true;
    }
    
    public boolean agregarEmpleado(Empleado empleado) {
        for (Empleado e: empleados) {
            if (e.getRut().equals(empleado.getRut())) {
                return false;
            }
        }
        empleados.add(empleado);
        return true;
    }

    @Override
    public String toString() {
        return "Sucursal{" + "id=" + id + ", nombre=" + nombre + ", ubicaci\u00f3n=" + ubicación + '}';
    } 
}
