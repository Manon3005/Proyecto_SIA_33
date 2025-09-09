/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model.services;

import com.model.domain.Empleado;
import com.model.repositories.EmpleadoRepository;
import java.util.Collection;

/**
 *
 * @author manon
 */
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepo;
    
    public EmpleadoService(EmpleadoRepository empleadoRepo) {
        this.empleadoRepo = empleadoRepo;
    }
    
    public Empleado conectarEmpleado(String rut, String contrasena) {
        Collection<Empleado> empleados = empleadoRepo.getEmpleados();
        for (Empleado e: empleados) {
            if (e.getRut().equals(rut) && e.getContrasena().equals(contrasena)) {
                return e;
            }
        }
        return null;
    }  
}
