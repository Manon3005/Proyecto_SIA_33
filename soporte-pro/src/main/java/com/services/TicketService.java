package com.services;

import com.domain.Cliente;
import com.domain.Empleado;
import com.domain.EstadoTicket;
import com.domain.Ticket;
import com.repositories.TicketRepository;

public class TicketService {
    private final TicketRepository ticketRepo;

    public TicketService(TicketRepository ticketRepo) {
        this.ticketRepo = ticketRepo;
    }
    
    public Ticket crearTicket(Cliente cliente, String titulo, String descripcion) {
        Ticket nuevoTicket = new Ticket(titulo, descripcion, cliente);
        if (ticketRepo.agregarTicket(nuevoTicket)) {
            cliente.agregarTicket(nuevoTicket);
            return nuevoTicket;
        }
        return null;
    }
    
    public Ticket crearTicket(Ticket nuevoTicket) {
        if (ticketRepo.agregarTicket(nuevoTicket)) {
            nuevoTicket.getCliente().agregarTicket(nuevoTicket);
            return nuevoTicket;
        }
        return null;
    }
    
    public boolean asignarTicketEmpleado(Empleado empleado, Ticket ticket){
        if (empleado.agregarTicket(ticket)) {
            ticket.setEmpleado(empleado);
            ticket.setEstado(EstadoTicket.EN_PROCESO);
            return true;
        }
        return false;
    }
}
