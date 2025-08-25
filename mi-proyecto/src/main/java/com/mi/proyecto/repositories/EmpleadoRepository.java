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

    // funicion que verifica y agrega un empleado que no este en la lista
    public boolean agregarEmpleado(Empleado empleado){
        if(buscarEmpleadoPorRut(empleado.getRut())!=null){
           return false; // Ya existe un empleado con el mismo rut 
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
    
    public Empleado empleadoConMasTickets(){
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
    
}
  /**
     * ???
     * 1. generar reporte de tickets del empleado
     * 2. generar reporte de los empleados
     * 3. cambiar estado de los tickets
     * 4. agregar empleado pero que se verifique que no esta en la lista
     * 5. quiza hacer una funcion de reasignar el ticket a un empleado que tenga menos trabajo 
     * ???
     */
    
