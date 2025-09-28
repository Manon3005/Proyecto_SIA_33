package com.model.repositories;

import com.model.domain.Empleado;
import com.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoRepository extends Repository {
    private List<Empleado> empleados;

    public EmpleadoRepository() {
        empleados = new ArrayList<>();
        cargarDatos();
    }   

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public boolean agregarEmpleado(Empleado empleado) {
        if (buscarPorRut(empleado.getRut()) != null) {
           return false;
        }
        return empleados.add(empleado);
    }
    
    public Empleado buscarPorRut(String rut) {
        for (Empleado e: empleados) {
            if (e.getRut().equals(rut)) {
                return e;
            }       
        }
        return null;
    }
       
    @Override
    public String toString() {
        String resultado;
        if (empleados.isEmpty()) {
            resultado = "No hay empleados registrados";
        } else {
            resultado = "";
            for (Empleado e : empleados) {
                resultado += "- " + e.toString() + "\n";
            }
        }
        return resultado;
    }
    
    @Override
    protected void cargarDatos() {
        String sql = "SELECT rut, contrasena, nombre, apellido, correo, salario FROM empleado";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Empleado e = new Empleado(
                    rs.getString("rut"),
                    rs.getString("contrasena"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("correo"),Double.valueOf(rs.getString("salario"))
                );
                empleados.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void guardarDatos() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "REPLACE INTO empleado (rut, contrasena, nombre, apellido, correo,salario) VALUES (?, ?, ?, ?, ?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            for (Empleado e : empleados) {
                stmt.setString(1, e.getRut());
                stmt.setString(2, e.getContrasena());
                stmt.setString(3, e.getNombre());
                stmt.setString(4, e.getApellido());
                stmt.setString(5, e.getCorreo());
                stmt.setString(6, String.valueOf(e.getSalario()));
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
