package com.view;

import com.controller.ClienteController;
import com.controller.LoginController;
import com.exceptions.NoConnectionToDataBaseException;
import com.model.repositories.ClienteRepository;
import com.model.repositories.EmpleadoRepository;
import com.model.repositories.TicketRepository;
import com.model.services.ClienteService;
import com.model.services.EmpleadoService;
import com.model.services.TicketService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.Alert;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static ClienteRepository clienteRepository;
    private static EmpleadoRepository empleadoRepository;
    private static TicketRepository ticketRepository;
    private static TicketService ticketService;
    private static ClienteService clienteService;
    private static EmpleadoService empleadoService;
    private static LoginController loginController;

    @Override
    public void start(Stage stage) throws IOException {
        clienteRepository = new ClienteRepository();
        empleadoRepository = new EmpleadoRepository();
        ticketRepository = new TicketRepository(clienteRepository, empleadoRepository);
        
        ticketService = new TicketService(ticketRepository, clienteRepository, empleadoRepository);
        clienteService = new ClienteService(clienteRepository);
        empleadoService = new EmpleadoService(empleadoRepository);       
               
        FXMLLoader fxmlLoaderLogin = new FXMLLoader(App.class.getResource("LoginPage.fxml"));
        Parent root = fxmlLoaderLogin.load();
        loginController = fxmlLoaderLogin.getController();
        loginController.setClienteService(clienteService);
        loginController.setEmpleadoService(empleadoService);
        loginController.setTicketService(ticketService);
          
        scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        cargarDatos();
    }

    public static void main(String[] args) {
        launch();
        clienteRepository.guardarDatos();
        empleadoRepository.guardarDatos();
        ticketRepository.guardarDatos();
    }
    
    private static void cargarDatos() {
        try {
            clienteRepository.cargarDatos();
            empleadoRepository.cargarDatos();
            ticketRepository.cargarDatos();
        } catch (NoConnectionToDataBaseException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(e.getMessage());
            alert.show();
        }
    }
    
    public static void logout(Stage stage) {
        loginController.clear();
        stage.setScene(scene);
    }
}