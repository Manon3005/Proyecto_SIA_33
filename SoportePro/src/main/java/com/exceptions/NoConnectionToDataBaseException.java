package com.exceptions;

public class NoConnectionToDataBaseException extends Exception {

    public NoConnectionToDataBaseException() {
        super("Error en la connexion a la base de datos, sus datos no seran guardadas en esta session");
    }
}
