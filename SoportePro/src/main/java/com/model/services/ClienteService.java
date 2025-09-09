package com.model.services;

import com.model.domain.Cliente;
import com.model.repositories.ClienteRepository;
import java.util.Collection;

public class ClienteService {

    private final ClienteRepository clienteRepo;
    
    public ClienteService(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }
    
    public Cliente conectarCliente(String rut, String contrasena) {
        Collection<Cliente> clientes = clienteRepo.getClientes().values();
        for (Cliente c: clientes) {
            if (c.getRut().equals(rut) && c.getContrasena().equals(contrasena)) {
                return c;
            }
        }
        return null;
    }  
}
