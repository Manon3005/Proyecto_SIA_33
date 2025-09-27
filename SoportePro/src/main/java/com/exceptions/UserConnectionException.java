package com.exceptions;

public class UserConnectionException extends Exception {

    public UserConnectionException() {
        super("Wrong rut o password.");
    }
}
