/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mi.proyecto.services;

import com.mi.proyecto.domain.Cliente;
import com.mi.proyecto.domain.Empleado;
import com.mi.proyecto.domain.EstadoTicket;
import com.mi.proyecto.domain.Ticket;
import com.mi.proyecto.repositories.TicketRepository;

/**
 *
 * @author manon
 */
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
    
    public boolean asignarTicketEmpleado(Empleado empleado, Ticket ticket){
        if (empleado.agregarTicket(ticket)) {
            ticket.setEmpleado(empleado);
            ticket.setEstado(EstadoTicket.EN_PROCESO);
            return true;
        }
        return false;
    }
}
