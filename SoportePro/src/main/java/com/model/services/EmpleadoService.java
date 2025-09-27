package com.model.services;

import com.exceptions.UserConnectionException;
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
    
    public Empleado conectarEmpleado(String rut, String contrasena) throws UserConnectionException {
        Collection<Empleado> empleados = empleadoRepo.getEmpleados();
        for (Empleado e: empleados) {
            if (e.getRut().equals(rut) && e.getContrasena().equals(contrasena)) {
                return e;
            }
        }
        throw new UserConnectionException();
    }  
}
