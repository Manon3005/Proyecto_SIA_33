/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mi.proyecto;

import com.mi.proyecto.repositories.SucursalRepository;
import com.mi.proyecto.repositories.ClienteRepository;
import com.mi.proyecto.repositories.EmpleadoRepository;
import com.mi.proyecto.repositories.TicketRepository;

/**
 *
 * @author manon
 */
public class MiProyecto {
    
    private ClienteRepository clienteRepo;
    private EmpleadoRepository empleadoRepo;
    private TicketRepository ticketRepo;
    private SucursalRepository sucursalRepo;
    
    public MiProyecto() {
        this.clienteRepo = new ClienteRepository();
        this.empleadoRepo = new EmpleadoRepository();
        this.ticketRepo = new TicketRepository();
        this.sucursalRepo = new SucursalRepository();
        
    }

    public static void main(String[] args) {
        new MiProyecto();
    }
}
