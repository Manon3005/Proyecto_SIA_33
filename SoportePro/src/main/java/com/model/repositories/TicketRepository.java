package com.model.repositories;

import com.model.domain.Cliente;
import com.model.domain.Empleado;
import com.model.domain.EstadoTicket;
import com.model.domain.Ticket;
import com.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TicketRepository extends Repository {
    private List<Ticket> tickets;
    private final ClienteRepository clienteRepo;
    private final EmpleadoRepository empleadoRepo;

    public TicketRepository(ClienteRepository clienteRepository, EmpleadoRepository empleadoRepository) {
        this.tickets = new ArrayList();
        this.clienteRepo = clienteRepository;
        this.empleadoRepo = empleadoRepository;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    
    public boolean agregarTicket(Ticket ticket){
       return tickets.add(ticket);
    }
    
    public Ticket buscarPorId(int id) {
        for (Ticket t: tickets) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }
    
    @Override
    public void cargarDatos() {
        String sql = "SELECT id, titulo, descripcion, estado, satisfaccion, fecha_creacion, fecha_finalizacion, cliente_rut, empleado_rut FROM ticket";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String clienteRut = rs.getString("cliente_rut");    
                String empleadoRut = rs.getString("empleado_rut");
                Cliente cliente = clienteRepo.buscarPorRut(clienteRut);
           
                Ticket ticket = new Ticket(rs.getString("titulo"), rs.getString("descripcion"), clienteRut);
                ticket.setId(rs.getInt("id"));
                cliente.agregarTicket(ticket);
                
                if (empleadoRut != null) {
                    Empleado empleado = empleadoRepo.buscarPorRut(empleadoRut);
                    empleado.agregarTicket(ticket);
                }

                ticket.setEmpleadoRut(empleadoRut);
                ticket.setEstado(EstadoTicket.valueOf(rs.getString("estado")));
                ticket.setSatisfaccion(rs.getInt("satisfaccion"));
                ticket.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());

                Timestamp fin = rs.getTimestamp("fecha_finalizacion");
                if (fin != null) {
                    ticket.setFechaFinalizacion(fin.toLocalDateTime());
                }
                agregarTicket(ticket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardarDatos() {
        String sqlReemplazo = "REPLACE INTO ticket (id, titulo, descripcion, estado, satisfaccion, fecha_creacion, fecha_finalizacion, cliente_rut, empleado_rut) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlSeleccionIds = "SELECT id FROM ticket";
        String sqlEliminar = "DELETE FROM ticket WHERE id = ?";

        try (Connection conn = DBConnection.getConnection()) {

            Set<Integer> idsEnBD = new HashSet<>();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlSeleccionIds)) {
                while (rs.next()) {
                    idsEnBD.add(rs.getInt("id"));
                }
            }

            Set<Integer> idsEnMemoria = tickets.stream()
                    .map(Ticket::getId)
                    .collect(Collectors.toSet());

            try (PreparedStatement stmtEliminar = conn.prepareStatement(sqlEliminar)) {
                for (Integer id : idsEnBD) {
                    if (!idsEnMemoria.contains(id)) {
                        stmtEliminar.setInt(1, id);
                        stmtEliminar.executeUpdate();
                    }
                }
            }

            try (PreparedStatement stmtReemplazo = conn.prepareStatement(sqlReemplazo)) {
                for (Ticket t : tickets) {
                    stmtReemplazo.setInt(1, t.getId());
                    stmtReemplazo.setString(2, t.getTitulo());
                    stmtReemplazo.setString(3, t.getDescripcion());
                    stmtReemplazo.setString(4, t.getEstado().toString());
                    stmtReemplazo.setObject(5, t.getSatisfaccion() == 0 ? null : t.getSatisfaccion());
                    stmtReemplazo.setTimestamp(6, Timestamp.valueOf(t.getFechaCreacion()));
                    stmtReemplazo.setTimestamp(7, t.getFechaFinalizacion() != null ? Timestamp.valueOf(t.getFechaFinalizacion()) : null);
                    stmtReemplazo.setString(8, t.getClienteRut());
                    stmtReemplazo.setString(9, t.getEmpleadoRut());

                    stmtReemplazo.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String toString(){
        String resultado;
        if (tickets.isEmpty()) {
            resultado = "No hay tickets registrados";
        } else {
            resultado = "";
            for (Ticket t : tickets) {
                resultado += "- " + t.toString() + "\n";
            }
        }
        return resultado;
    }
}
