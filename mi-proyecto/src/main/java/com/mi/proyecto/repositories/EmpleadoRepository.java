/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mi.proyecto.repositories;

import com.mi.proyecto.domain.Empleado;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author manon
 */
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

    public boolean agregarEmpleado(Empleado empleado){
        if(buscarPorRut(empleado.getRut())!=null){
           return false;
        }
        return empleados.add(empleado);
    }
    
    public Empleado buscarPorRut(String rut){
        for(Empleado emp: empleados){
         if(emp.getRut().equals(rut)){
             return emp;
         }   
        }
        return null;
    }
       
    @Override
    public String toString(){
        String resultado;
        if(empleados.isEmpty()){
            resultado = "No hay empleados registrados";
        }else{
            resultado = "";
            for(Empleado e : empleados){
                resultado += "- " + e.toString() + "\n";
            }
        }
        return resultado;
    }
}
