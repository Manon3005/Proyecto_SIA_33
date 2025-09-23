/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controller;

import com.model.domain.Cliente;
import com.model.domain.Empleado;
import com.model.services.ClienteService;
import com.model.services.EmpleadoService;
import com.model.services.TicketService;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private TicketService ticketService;
        
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

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }
       
    public void login() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setHeaderText("Wrong rut o password");
        if (userTypeChoiceBox.getValue().equals("Cliente")) {
            Cliente cliente = clienteService.conectarCliente(rutField.getText(), passwordField.getText());
            if(cliente == null) {
                alert.show();
            } else {
                redirectToClientePage(cliente);
            }
        } else {
            Empleado empleado = empleadoService.conectarEmpleado(rutField.getText(), passwordField.getText());
            if(empleado == null) {
                alert.show();
            } else {
                redirectToEmpleadoPage(empleado);
            }
        }
    }
    
    private void redirectToClientePage(Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/view/ClientePage.fxml"));
            Parent root = loader.load();

            ClienteController clienteController = loader.getController();
            clienteController.setTicketService(ticketService);
            clienteController.setCliente(cliente);
            clienteController.actualizarTicketList();

            Stage stage = (Stage) rutField.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void redirectToEmpleadoPage(Empleado empleado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/view/EmpleadoPage.fxml"));
            Parent root = loader.load();

            EmpleadoController empleadoController = loader.getController();
            empleadoController.setEmpleado(empleado);

            Stage stage = (Stage) rutField.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
