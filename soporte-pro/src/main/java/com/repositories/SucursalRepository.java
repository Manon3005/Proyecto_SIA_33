package com.repositories;

import com.domain.Sucursal;
import java.util.ArrayList;
import java.util.List;

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
    
    public boolean agregarSucursal(Sucursal sucursal) {
       sucursal.setId(sucursales.size());
       return sucursales.add(sucursal);
    }
}
