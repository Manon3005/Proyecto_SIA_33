/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mi.proyecto.repositories;

import com.mi.proyecto.domain.Cliente;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author manon
 */
public class ClienteRepository {
    private List<Cliente> clientes;

    public ClienteRepository() {
        clientes = new ArrayList<>();
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
    
   public boolean agregarCliente(Cliente cliente){
       if(cliente == null || cliente.getRut()== null || cliente.getRut().isEmpty()){
           System.out.println("Cliente invalido, no se puede agregar.");
           return false;
       }
       
       for(Cliente c : clientes){
           if(c.getRut().equals(cliente.getRut())){
               System.out.println("El cliente con rut: "+cliente.getRut()+" ya existe.");
               return false;
           }
       }
       clientes.add(cliente);
       return true;
   }
    
    public Cliente buscarPorRut(String rut){
       for(Cliente c: clientes){
           if(c.getRut().equals(rut)){
               return c;
           }
       }
       return null;
    }
    
    public boolean actualizarCliente(String rut,Cliente nuevoCliente){
       for(int i=0;i<clientes.size();i++){
           if(clientes.get(i).getRut().equals(rut)){
               clientes.set(i, nuevoCliente);
               return true;
           }
       }
       return false;
   }

    public Cliente ClienteConMasTicket(){
       if(clientes ==null||clientes.isEmpty()){
           System.out.println("No hay clientes registrados");
           return null;
       }
       Cliente maxCliente = null;
       int maxTickets = 0;
       for(Cliente c : clientes){
          if(c!=null && c.getTickets()!= null){
            int cantTickets = c.getTickets().size();
            if(cantTickets > maxTickets){
                maxTickets = cantTickets;
                maxCliente = c;
            }
          }
       }
       if(maxTickets == 0){
           System.out.println("Ningun cliente tiene tickets");
       }
       return maxCliente;// cliente con mas tickets
   }

    
    public void listarClientes(){
       if(clientes.isEmpty()){
           System.out.println("No hay clientes registrados");
       }else{
           for(Cliente c : clientes){
               System.out.println(c.getRut()+" - "+ c.getNombre() +" "+c.getApellido());
           }
       }
    }

    
    // esto deberia ir aqui? o en repositorio ticket
   // sera necesario esta metodo?
    
   public boolean agregarTicketACliente(String rut,Ticket ticket){
       Cliente cliente = buscarPorRut(rut);
       if(cliente ==null){
           System.out.println("No se encontro el cliente con rut "+ rut);
           return false;
       }
       
       cliente.getTickets().add(ticket);
       return true;
   }
   
  /**
   * ???
   * 1.obtener tickets del cliente
   * 2.podriamos calcular el promedio de satisfaccion del cliente
   * 3.obtener todos los clientes con tickets abiertos para seguir los casos activos
   * ???
   */
    
    
    
}
