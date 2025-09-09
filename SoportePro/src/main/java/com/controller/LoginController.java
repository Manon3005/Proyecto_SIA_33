/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.model.services.ClienteService;
import com.model.services.EmpleadoService;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author manon
 */
public class LoginController {
    @FXML
    TextField rutField;
    
    @FXML
    TextField passwordField;
    
    @FXML
    ChoiceBox userTypeChoiceBox;
    
    private ClienteService clienteService;
    private EmpleadoService empleadoService;
        
    public void initialize() {
        ObservableList<String> options = FXCollections.observableArrayList(
            "Cliente",
            "Empleado"
        );

        userTypeChoiceBox.setItems(options);
        userTypeChoiceBox.setValue("Cliente");
    }
    
    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    
    public void setEmpleadoService(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }
    
    public void login() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setHeaderText("Wrong rut o password");
        if (userTypeChoiceBox.getValue().equals("Cliente")) {
            if(clienteService.conectarCliente(rutField.getText(), passwordField.getText()) == null) {
                alert.show();
            } else {
                redirectTo("ClientePage.fxml");
            }
        } else {
            if(empleadoService.conectarEmpleado(rutField.getText(), passwordField.getText()) == null) {
                alert.show();
            } else {
                redirectTo("EmpleadoPage.fxml");
            }
        }
    }
    
    private void redirectTo(String fxmlFile) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/com/view/" + fxmlFile));

        Stage stage = (Stage) rutField.getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
