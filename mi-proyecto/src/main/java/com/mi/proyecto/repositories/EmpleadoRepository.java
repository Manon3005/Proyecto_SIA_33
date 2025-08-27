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

    // funcion que verifica y agrega un empleado que no este en la lista
    public boolean agregarEmpleado(Empleado empleado){
        if(buscarEmpleadoPorRut(empleado.getRut())!=null){
           return false; // ya existe un empleado con el mismo rut 
        }
        return empleados.add(empleado);
    }
    
    public Empleado buscarEmpleadoPorRut(String rut){
        for(Empleado emp: empleados){
         if(emp.getRut().equals(rut)){
             return emp;
         }   
        }
        return null;
    }
    
    public Empleado getEmpleadoConMasTickets(){
        if(empleados.isEmpty()){
            System.out.println("No hay empleados registrados");
           return null;
        }
        
        Empleado maxEmpleado = empleados.get(0);
        int maxTickets = maxEmpleado.getTickets()!= null ? maxEmpleado.getTickets().size() : 0;
        
        for(Empleado emp : empleados){
          if(emp!=null){
            int cantTickets = emp.getTickets() != null ? emp.getTickets().size() : 0;
            
            if(cantTickets > maxTickets){
                maxTickets = cantTickets;
                maxEmpleado = emp;
            }    
           }
        }
        
        // Verificar si al menos un empleado tiene tickets
        if(maxTickets == 0){
            System.out.println("Ningun empleado tiene tickets asignados");
            return null;
        }
        
        return maxEmpleado;  
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
