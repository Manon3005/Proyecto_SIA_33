package com.view;

import com.controller.ClienteController;
import com.controller.LoginController;
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

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static ClienteRepository clienteRepository = new ClienteRepository();
    private static EmpleadoRepository empleadoRepository = new EmpleadoRepository();
    private static TicketRepository ticketRepository = new TicketRepository();
    private static TicketService ticketService;
    private static ClienteService clienteService;
    private static EmpleadoService empleadoService;

    @Override
    public void start(Stage stage) throws IOException {
        ticketService = new TicketService(ticketRepository, clienteRepository, empleadoRepository);
        clienteService = new ClienteService(clienteRepository);
        empleadoService = new EmpleadoService(empleadoRepository);
               
        FXMLLoader fxmlLoaderLogin = new FXMLLoader(App.class.getResource("LoginPage.fxml"));
        Parent root = fxmlLoaderLogin.load();
        LoginController loginController = fxmlLoaderLogin.getController();
        loginController.setClienteService(clienteService);
        loginController.setEmpleadoService(empleadoService);
        loginController.setTicketService(ticketService);
          
        scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        clienteRepository.guardarDatos();
        empleadoRepository.guardarDatos();
        ticketService.guardarDatos();
    }

}