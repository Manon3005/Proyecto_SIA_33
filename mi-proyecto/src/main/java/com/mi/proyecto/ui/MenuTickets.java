/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mi.proyecto.ui;

/**
 *
 * @author manon
 */
import com.mi.proyecto.domain.Cliente;
import com.mi.proyecto.domain.Ticket;
import com.mi.proyecto.repositories.ClienteRepository;
import java.io.BufferedReader;
import com.mi.proyecto.services.TicketService;
import java.io.IOException;
import java.io.InputStreamReader;

public class MenuTickets {
    private ClienteRepository clienteRepo;
    private TicketService ticketService;
    private BufferedReader lector;

    public MenuTickets(ClienteRepository clienteRepo, TicketService ticketService) {
        this.clienteRepo = clienteRepo;
        this.ticketService = ticketService;
        this.lector = new BufferedReader(new InputStreamReader(System.in));
    }

    public void mostrarMenu() throws IOException {
        int opcion;
        do {
            System.out.println("\n=== MENU DE TICKETS ===");
            System.out.println("1. Agregar nuevo Ticket a un Cliente");
            System.out.println("2. Listar Tickets de un Cliente");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            String input = lector.readLine();
            if (input == null || input.trim().isEmpty()) {
                System.out.println("Debe ingresar un numero. Intente nuevamente.");
                opcion = -1;
            } else {
                opcion = Integer.parseInt(input);

                switch (opcion) {
                    case 1:
                        agregarTicket();
                        break;
                    case 2:
                        listarTickets();
                        break;
                    case 0:
                        System.out.println("Saliendo del menu...");
                        break;
                    default:
                        System.out.println("Opcion invalida, intente nuevamente.");
                }
            }
        } while (opcion != 0);
    }

    private void agregarTicket() throws IOException {
        System.out.println("\n--- AGREGAR TICKET ---");
        System.out.print("Ingrese RUT del Cliente: ");
        String rut = lector.readLine();

        Cliente cliente = clienteRepo.buscarPorRut(rut);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.print("Titulo del ticket: ");
        String titulo = lector.readLine();

        System.out.print("Descripcion del ticket: ");
        String descripcion = lector.readLine();

        if (ticketService.crearTicket(cliente, titulo, descripcion) != null) {
            System.out.println("Ticket creado!");
        } else {
            System.out.println("Error al crear el ticket.");
        }
    }

    private void listarTickets() throws IOException {
        System.out.println("\n--- LISTAR TICKETS DE CLIENTE ---");
        System.out.print("Ingrese RUT del Cliente: ");
        String rut = lector.readLine();

        Cliente cliente = clienteRepo.buscarPorRut(rut);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        if (cliente.getTickets().isEmpty()) {
            System.out.println("El cliente no tiene tickets registrados.");
        } else {
            System.out.println("Tickets de " + cliente.getNombre() + ":");
            for (Ticket t : cliente.getTickets()) {
                System.out.println("- [" + t.getId() + "] " + t.getTitulo() + " | Estado: " + t.getEstado());
            }
        }
    }
}

