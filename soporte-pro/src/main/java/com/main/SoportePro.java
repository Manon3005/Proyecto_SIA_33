package com.main;

import com.domain.Cliente;
import com.domain.Empleado;
import com.domain.Sucursal;
import com.domain.Ticket;
import com.repositories.SucursalRepository;
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
    private static SucursalRepository sucursalRepo;
    private static TicketService ticketService;
    
    public SoportePro() {
        SoportePro.clienteRepo = new ClienteRepository();
        SoportePro.empleadoRepo = new EmpleadoRepository();
        SoportePro.ticketRepo = new TicketRepository();
        SoportePro.sucursalRepo = new SucursalRepository();
        SoportePro.ticketService = new TicketService(ticketRepo);
        
    }

    public static void main(String[] args) throws IOException {
        new SoportePro();
        SoportePro.initializarDatos();
        MenuTickets menu = new MenuTickets(clienteRepo, ticketService);
        menu.mostrarMenu();
    }
    
    private static void initializarDatos() {
        Sucursal s1 = new Sucursal(1, "Sucursal Santiago Centro", "Santiago");
        Sucursal s2 = new Sucursal(2, "Sucursal Valparaiso", "Valparaiso");
        sucursalRepo.agregarSucursal(s1);
        sucursalRepo.agregarSucursal(s2);
        
        Cliente c1 = new Cliente("11111111-1", "Juan", "Perez", "juan.perez@mail.com");
        Cliente c2 = new Cliente("22222222-2", "Maria", "Gonzalez", "maria.gonzalez@mail.com");
        Cliente c3 = new Cliente("33333333-3", "Luis", "Ramirez", "luis.ramirez@mail.com");
        clienteRepo.agregarCliente(c1);
        clienteRepo.agregarCliente(c2);
        clienteRepo.agregarCliente(c3);
        
        Empleado e1 = new Empleado("44444444-4", "Ana", "Lopez", "ana.lopez@empresa.com");
        Empleado e2 = new Empleado("55555555-5", "Pedro", "Sanchez", "pedro.sanchez@empresa.com");
        empleadoRepo.agregarEmpleado(e1);
        empleadoRepo.agregarEmpleado(e2);
        
        Ticket t1 = ticketService.crearTicket(c1,"Problema Internet", "No tiene conexion en su domicilio.");
        Ticket t2 = ticketService.crearTicket(c1, "Error Factura", "Monto incorrecto en la ultima boleta.");
        Ticket t3 = ticketService.crearTicket(c2, "Cambio Plan", "Desea cambiar a un plan mas economico.");
        
        s1.agregarCliente(c1);
        s1.agregarCliente(c2);
        s2.agregarCliente(c3);
        s1.agregarEmpleado(e1);
        s2.agregarEmpleado(e2);
        
        ticketService.asignarTicketEmpleado(e1, t1);
        ticketService.asignarTicketEmpleado(e1, t2);
    }
}
