package com.model.services;

import com.model.domain.Cliente;
import com.model.domain.Empleado;
import com.model.domain.EstadoTicket;
import com.model.domain.Ticket;
import com.model.repositories.ClienteRepository;
import com.model.repositories.EmpleadoRepository;
import com.model.repositories.TicketRepository;
import com.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class TicketService {
    private final TicketRepository ticketRepo;
    private final ClienteRepository clienteRepo;
    private final EmpleadoRepository empleadoRepo;

    public TicketService(TicketRepository ticketRepo, ClienteRepository clienteRepository, EmpleadoRepository empleadoRepository) {
        this.ticketRepo = ticketRepo;
        this.clienteRepo = clienteRepository;
        this.empleadoRepo = empleadoRepository;
        cargarDatos();
    }
    
    public Ticket crearTicket(String clienteRut, String titulo, String descripcion) {
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
    
    private void cargarDatos() {
        String sql = "SELECT id, titulo, descripcion, estado, satisfaccion, fecha_creacion, fecha_finalizacion, cliente_rut, empleado_rut FROM ticket";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String clienteRut = rs.getString("cliente_rut");

                Ticket ticket = crearTicket(clienteRut, rs.getString("titulo"), rs.getString("descripcion"));

                ticket.setId(rs.getInt("id"));
                ticket.setEstado(EstadoTicket.valueOf(rs.getString("estado")));
                ticket.setSatisfaccion(rs.getInt("satisfaccion"));
                ticket.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());

                Timestamp fin = rs.getTimestamp("fecha_finalizacion");
                if (fin != null) {
                    ticket.setFechaFinalizacion(fin.toLocalDateTime());
                }

                String empleadoRut = rs.getString("empleado_rut");
                if (empleadoRut != null) {
                    asignarTicketEmpleado(empleadoRut, ticket);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void guardarDatos() {
        String sql = "REPLACE INTO ticket (id, titulo, descripcion, estado, satisfaccion, fecha_creacion, fecha_finalizacion, cliente_rut, empleado_rut) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (Ticket t : ticketRepo.getTickets()) {
                stmt.setInt(1, t.getId());
                stmt.setString(2, t.getTitulo());
                stmt.setString(3, t.getDescripcion());
                stmt.setString(4, t.getEstado().toString());
                stmt.setObject(5, t.getSatisfaccion() == 0 ? null : t.getSatisfaccion());
                stmt.setTimestamp(6, Timestamp.valueOf(t.getFechaCreacion()));
                stmt.setTimestamp(7, t.getFechaFinalizacion() != null ? Timestamp.valueOf(t.getFechaFinalizacion()) : null);
                stmt.setString(8, t.getClienteRut());
                stmt.setString(9, t.getEmpleadoRut());

                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
