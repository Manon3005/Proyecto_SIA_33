package com.model.repositories;

import com.exceptions.NoConnectionToDataBaseException;

public abstract class Repository {
    
    public abstract void cargarDatos() throws NoConnectionToDataBaseException;
    public abstract void guardarDatos();
}
