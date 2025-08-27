/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mi.proyecto.repositories;

import com.mi.proyecto.domain.Sucursal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author manon
 */
public class SucursalRepository {
    private List<Sucursal> sucursales;
    
    public SucursalRepository() {
        sucursales = new ArrayList<>();
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }
    
    public boolean agregarSucursal(Sucursal sucursal){
       sucursal.setId(sucursales.size());
       return sucursales.add(sucursal);
    }
}
