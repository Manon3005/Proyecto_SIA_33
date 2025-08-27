/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mi.proyecto;

import com.mi.proyecto.domain.Cliente;
import com.mi.proyecto.domain.Empleado;
import com.mi.proyecto.domain.Sucursal;
import com.mi.proyecto.domain.Ticket;
import com.mi.proyecto.repositories.SucursalRepository;
import com.mi.proyecto.repositories.ClienteRepository;
import com.mi.proyecto.repositories.EmpleadoRepository;
import com.mi.proyecto.repositories.TicketRepository;
import com.mi.proyecto.services.TicketService;
import com.mi.proyecto.ui.MenuTickets;
import java.io.IOException;

/**
 *
 * @author manon
 */
public class MiProyecto {
    
    private static ClienteRepository clienteRepo;
    private static EmpleadoRepository empleadoRepo;
    private static TicketRepository ticketRepo;
    private static SucursalRepository sucursalRepo;
    private static TicketService ticketService;
    
    public MiProyecto() {
        MiProyecto.clienteRepo = new ClienteRepository();
        MiProyecto.empleadoRepo = new EmpleadoRepository();
        MiProyecto.ticketRepo = new TicketRepository();
        MiProyecto.sucursalRepo = new SucursalRepository();
        MiProyecto.ticketService = new TicketService(ticketRepo);
        
    }

    public static void main(String[] args) throws IOException {
        new MiProyecto();
        MiProyecto.initializarDatos();
        MenuTickets menu = new MenuTickets(clienteRepo, ticketService);
        menu.mostrarMenu();
    }
    
    private static void initializarDatos() {
        Sucursal s1 = new Sucursal(1, "Sucursal Santiago Centro", "Santiago");
        Sucursal s2 = new Sucursal(2, "Sucursal Valparaíso", "Valparaíso");
        sucursalRepo.agregarSucursal(s1);
        sucursalRepo.agregarSucursal(s2);
        
        Cliente c1 = new Cliente("11111111-1", "Juan", "Pérez", "juan.perez@mail.com");
        Cliente c2 = new Cliente("22222222-2", "María", "González", "maria.gonzalez@mail.com");
        Cliente c3 = new Cliente("33333333-3", "Luis", "Ramírez", "luis.ramirez@mail.com");
        clienteRepo.agregarCliente(c1);
        clienteRepo.agregarCliente(c2);
        clienteRepo.agregarCliente(c3);
        
        Empleado e1 = new Empleado("44444444-4", "Ana", "López", "ana.lopez@empresa.com");
        Empleado e2 = new Empleado("55555555-5", "Pedro", "Sánchez", "pedro.sanchez@empresa.com");
        empleadoRepo.agregarEmpleado(e1);
        empleadoRepo.agregarEmpleado(e2);
        
        Ticket t1 = ticketService.crearTicket(c1,"Problema Internet", "No tiene conexión en su domicilio.");
        Ticket t2 = ticketService.crearTicket(c1, "Error Factura", "Monto incorrecto en la última boleta.");
        Ticket t3 = ticketService.crearTicket(c2, "Cambio Plan", "Desea cambiar a un plan más económico.");
        
        s1.agregarCliente(c1);
        s1.agregarCliente(c2);
        s2.agregarCliente(c3);
        s1.agregarEmpleado(e1);
        s2.agregarEmpleado(e2);
        
        ticketService.asignarTicketEmpleado(e1, t1);
        ticketService.asignarTicketEmpleado(e1, t2);
    }
}
