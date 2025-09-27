package com.model.services;

import com.exceptions.InformationMissingException;
import com.model.domain.Cliente;
import com.model.domain.Empleado;
import com.model.domain.EstadoTicket;
import com.model.domain.Ticket;
import com.model.repositories.ClienteRepository;
import com.model.repositories.EmpleadoRepository;
import com.model.repositories.TicketRepository;

public class TicketService {
    private final TicketRepository ticketRepo;
    private final ClienteRepository clienteRepo;
    private final EmpleadoRepository empleadoRepo;

    public TicketService(TicketRepository ticketRepo, ClienteRepository clienteRepository, EmpleadoRepository empleadoRepository) {
        this.ticketRepo = ticketRepo;
        this.clienteRepo = clienteRepository;
        this.empleadoRepo = empleadoRepository;
    }
    
    public Ticket crearTicket(String clienteRut, String titulo, String descripcion) throws InformationMissingException {
        if (titulo.isBlank() || descripcion.isBlank()) {
            throw new InformationMissingException("Falta informacion para crear el ticket.");
        }
        Cliente cliente = clienteRepo.buscarPorRut(clienteRut);
        if (cliente == null) {
            return null;
        }
        Ticket nuevoTicket = new Ticket(titulo, descripcion, clienteRut);
        nuevoTicket.setId(ticketRepo.getTickets().size());
        if (ticketRepo.agregarTicket(nuevoTicket)) {
            cliente.agregarTicket(nuevoTicket);
            return nuevoTicket;
        }
        return null;
    }
      
    public boolean asignarTicketEmpleado(String empleadoRut, Ticket ticket){
        Empleado empleado = empleadoRepo.buscarPorRut(empleadoRut);
        if (empleado != null && empleado.agregarTicket(ticket)) {
            ticket.setEmpleadoRut(empleado.getRut());
            ticket.setEstado(EstadoTicket.EN_PROCESO);
            return true;
        }
        return false;
    }
    
    public void eliminarTicket(Ticket ticket) {
        String clienteRut = ticket.getClienteRut();
        Cliente cliente = clienteRepo.buscarPorRut(clienteRut);
        cliente.getTickets().remove(ticket);
        ticketRepo.getTickets().remove(ticket);
    }
}
