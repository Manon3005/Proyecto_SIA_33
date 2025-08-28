package com.repositories;

import com.domain.Empleado;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoRepository {
    private List<Empleado> empleados;

    public EmpleadoRepository() {
        empleados = new ArrayList<>();
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
}
