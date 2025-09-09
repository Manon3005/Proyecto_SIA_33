package com.view;

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

    @Override
    public void start(Stage stage) throws IOException {
        
        ClienteRepository clienteRepository = new ClienteRepository();
        EmpleadoRepository empleadoRepository = new EmpleadoRepository();
        TicketRepository ticketRepository = new TicketRepository();
        TicketService ticketService = new TicketService(ticketRepository, clienteRepository, empleadoRepository);
        ClienteService clienteService = new ClienteService(clienteRepository);
        EmpleadoService empleadoService = new EmpleadoService(empleadoRepository);
        
        //clienteRepository.guardarDatos();
        //empleadoRepository.guardarDatos();
        //ticketService.guardarDatos();
        
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("LoginPage.fxml"));
        scene = new Scene(fxmlLoader.load(), 900, 600);
        LoginController loginController = fxmlLoader.getController();
        loginController.setClienteService(clienteService);
        loginController.setEmpleadoService(empleadoService);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}