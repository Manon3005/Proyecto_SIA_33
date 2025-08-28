/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mi.proyecto.repositories;

import com.mi.proyecto.domain.Cliente;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author manon
 */
public class ClienteRepository {
    private Map<String, Cliente> clientes = new HashMap<>();

    public ClienteRepository() {
        clientes = new HashMap<>();
    }

    public Map<String, Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(Map<String, Cliente> clientes) {
        this.clientes = clientes;
    }
    
    public boolean agregarCliente(Cliente cliente) {
        if(cliente == null || cliente.getRut()== null || cliente.getRut().isEmpty()){
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
    
    public Cliente buscarPorRut(String rut){
       return clientes.get(rut);
    }
    
    public void actualizarCliente(Cliente nuevoCliente){
        clientes.replace(nuevoCliente.getRut(), nuevoCliente);
    }
    
    public void actualizarCliente(String rut, String nuevoNombre, String nuevoApellido, String nuevoCorreo){
        Cliente cliente = clientes.get(rut);
        if (cliente == null) {
            return;
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
    }
    
    @Override
    public String toString(){
        String resultado;
        if(clientes.isEmpty()){
            resultado = "No hay clientes registrados";
        }else{
            resultado = "";
            for(Cliente c : clientes.values()){
                resultado += "- " + c.toString() + "\n";
            }
        }
        return resultado;
    }
}
