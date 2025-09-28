package com.model.services;

import com.exceptions.UserConnectionException;
import com.model.domain.Empleado;
import com.model.domain.EstadoTicket;
import com.model.domain.Ticket;
import com.model.repositories.EmpleadoRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    
    public void cargarseTicket(Empleado empleado, Ticket ticket) {
        empleado.agregarTicket(ticket);
        ticket.setEmpleadoRut(empleado.getRut());
        ticket.setEstado(EstadoTicket.EN_PROCESO);
    }
    
    public void terminarTicket(Ticket ticket) {
        ticket.setFechaFinalizacion(LocalDateTime.now(ZoneId.of("America/Santiago")));
        ticket.setEstado(EstadoTicket.EN_EVALUACION);
    }
}
