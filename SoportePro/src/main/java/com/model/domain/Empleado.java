package com.model.domain;

public class Empleado extends Usuario {
    
    private double salario;

    public Empleado(String rut, String contrasena, String nombre, String apellido, String correo, Double salario){
        super(rut, contrasena, nombre, apellido, correo);
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "Empleado{" + "rut=" + rut + ", nombre=" + nombre + ", apellido=" + apellido + ", correo=" + correo +",salario ='"+salario+'}';
    }
    
    public double getSalario(){
        return salario;
    }
    public void setSalario(Double salary){
        this.salario = salary;
    }
    
}
