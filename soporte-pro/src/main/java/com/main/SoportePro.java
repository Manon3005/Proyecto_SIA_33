package com.main;

import com.repositories.ClienteRepository;
import com.repositories.EmpleadoRepository;
import com.repositories.TicketRepository;
import com.services.TicketService;
import com.ui.MenuTickets;
import java.io.IOException;

public class SoportePro {
    
    private static ClienteRepository clienteRepo;
    private static EmpleadoRepository empleadoRepo;
    private static TicketRepository ticketRepo;
    private static TicketService ticketService;
    
    public SoportePro() {
        SoportePro.clienteRepo = new ClienteRepository();
        SoportePro.empleadoRepo = new EmpleadoRepository();
        SoportePro.ticketRepo = new TicketRepository();
        SoportePro.ticketService = new TicketService(ticketRepo, clienteRepo, empleadoRepo);
        
    }

    public static void main(String[] args) throws IOException {
        new SoportePro();
        MenuTickets menu = new MenuTickets(clienteRepo, ticketService);
        menu.mostrarMenu();
        clienteRepo.guardarDatos();
        empleadoRepo.guardarDatos();
        ticketService.guardarDatos();
    }
}
