package com.model.repositories;

import com.model.domain.Cliente;
import com.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ClienteRepository extends Repository {
    private Map<String, Cliente> clientes = new HashMap<>();

    public ClienteRepository() {
        clientes = new HashMap<>();
        cargarDatos();
    }

    public Map<String, Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(Map<String, Cliente> clientes) {
        this.clientes = clientes;
    }
    
    public boolean agregarCliente(Cliente cliente) {
        if (cliente == null || cliente.getRut()== null || cliente.getRut().isEmpty()) {
           System.out.println("Cliente invalido, no se puede agregar.");
           return false;
        }
        if (clientes.containsKey(cliente.getRut())) {
            System.out.println("Cliente con RUT ya existe: " + cliente.getRut());
            return false;
        }
        clientes.put(cliente.getRut(), cliente);
        return true;
    }
    
    public Cliente buscarPorRut(String rut) {
       return clientes.get(rut);
    }
    
    public boolean actualizarCliente(Cliente nuevoCliente) {
        if (clientes.containsKey(nuevoCliente.getRut())) {
            clientes.replace(nuevoCliente.getRut(), nuevoCliente);
            return true;
        }
        return false;
    }
    
    public boolean actualizarCliente(String rut, String nuevoNombre, String nuevoApellido, String nuevoCorreo) {
        Cliente cliente = clientes.get(rut);
        if (cliente == null) {
            return false;
        }
        if (nuevoNombre!= null && !nuevoNombre.isEmpty()) {
            cliente.setNombre(nuevoNombre);
        }
        if (nuevoApellido!= null && !nuevoApellido.isEmpty()) {
            cliente.setApellido(nuevoApellido);
        }
        if (nuevoCorreo!= null && !nuevoCorreo.isEmpty()) {
            cliente.setCorreo(nuevoCorreo);
        }
        return true;
    }
    
    @Override
    public String toString() {
        String resultado;
        if (clientes.isEmpty()) {
            resultado = "No hay clientes registrados";
        } else {
            resultado = "";
            for(Cliente c : clientes.values()) {
                resultado += "- " + c.toString() + "\n";
            }
        }
        return resultado;
    }
    
    @Override
    protected void cargarDatos() {
        String sql = "SELECT * FROM cliente";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente c = new Cliente(
                    rs.getString("rut"),
                    rs.getString("contrasena"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("correo")
                );
                clientes.put(c.getRut(), c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void guardarDatos() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "REPLACE INTO cliente (rut, contrasena, nombre, apellido, correo) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            for (Cliente c : clientes.values()) {
                stmt.setString(1, c.getRut());
                stmt.setString(2, c.getContrasena());
                stmt.setString(3, c.getNombre());
                stmt.setString(4, c.getApellido());
                stmt.setString(5, c.getCorreo());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
