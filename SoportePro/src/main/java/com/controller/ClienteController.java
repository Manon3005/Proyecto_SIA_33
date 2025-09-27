/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller;

import com.exceptions.InformationMissingException;
import com.model.domain.Cliente;
import com.model.domain.Empleado;
import com.model.domain.EstadoTicket;
import com.model.domain.Ticket;
import com.model.services.TicketService;
import com.util.CsvUtils;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author manon
 */
public class ClienteController implements Initializable {
    
    @FXML
    Text bienvenidoText;
    
    @FXML
    TextField nuevoTituloField;
    
    @FXML
    TextArea nuevaDescripcionTextArea;
    
    @FXML
    ListView ticketList;
    
    @FXML
    TextField ticketTituloField;
    
    @FXML
    TextArea ticketDescripcionTextArea;
    
    @FXML
    Text estadoText;
    
    @FXML
    Button modificarTicketButton;
    
    @FXML
    Button eliminarTicketButton;
    
    @FXML
    Button estimarSatisfaccionButton;
    
    @FXML
    Slider ticketSatisfaccionSlider;
    
    @FXML
    Button exportarTicketButton;
    
    @FXML
    TextField ticketSearchBar;
    
    @FXML
    Button buscarTicketButton;
    
    private Cliente cliente;
    private TicketService ticketService;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ticketList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ticketList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                mostrarDetalles((Ticket) newSelection);
            }
        });     
        
        ticketSatisfaccionSlider.setMin(1);
        ticketSatisfaccionSlider.setMax(5);
        ticketSatisfaccionSlider.setValue(5);

        ticketSatisfaccionSlider.setShowTickMarks(true);
        ticketSatisfaccionSlider.setShowTickLabels(true);
        ticketSatisfaccionSlider.setMajorTickUnit(1);
        ticketSatisfaccionSlider.setMinorTickCount(0); 
        ticketSatisfaccionSlider.setSnapToTicks(true); 
        
        eliminarDetalles();
    }    
    
    public void crearTicket() {
        try {
            ticketService.crearTicket(cliente.getRut(), nuevoTituloField.getText(),  nuevaDescripcionTextArea.getText());
            actualizarTicketList("");
            nuevoTituloField.clear();
            nuevaDescripcionTextArea.clear();
        } catch (InformationMissingException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Debes completar título y descripción!");
            alert.show();
        }
    }
       
    public void actualizarTicketList(String research) {
        ticketList.setItems(FXCollections.observableArrayList());
        System.out.println(cliente);
        List<Ticket> tickets = filtrarPorTitulo(cliente.getTickets(), research);
        for (Ticket ticket: tickets) {
            ticketList.getItems().add(ticket);
        }
        if (cliente.getTickets().isEmpty()) {
            exportarTicketButton.setDisable(true);
            ticketSearchBar.setDisable(true);
            buscarTicketButton.setDisable(true);
        } else {
            exportarTicketButton.setDisable(false);
            ticketSearchBar.setDisable(false);
            buscarTicketButton.setDisable(false);
        }
    }
    
    public void mostrarDetalles(Ticket ticket) {
        ticketTituloField.setText(ticket.getTitulo());
        ticketDescripcionTextArea.setText(ticket.getDescripcion());
        estadoText.setText(ticket.getEstado().toString());
        
        if(ticket.getSatisfaccion() != 0) {
            ticketSatisfaccionSlider.setValue(ticket.getSatisfaccion());
        } else {
            ticketSatisfaccionSlider.setValue(5);
        }
        
        if (ticket.getEstado() == EstadoTicket.EN_EVALUACION) {
            estimarSatisfaccionButton.setDisable(false);
            ticketSatisfaccionSlider.setDisable(false);
        } else {
            estimarSatisfaccionButton.setDisable(true);
            ticketSatisfaccionSlider.setDisable(true);
        }
        
        if (ticket.getEstado() == EstadoTicket.PENDIENTE) {
            modificarTicketButton.setDisable(false);
            eliminarTicketButton.setDisable(false);
        } else {
            modificarTicketButton.setDisable(true);
            eliminarTicketButton.setDisable(true);
        }
    }
    
    public void eliminarDetalles() {
        ticketTituloField.clear();
        ticketDescripcionTextArea.clear();
        estadoText.setText("");
        modificarTicketButton.setDisable(true);
        eliminarTicketButton.setDisable(true);
        estimarSatisfaccionButton.setDisable(true);
        ticketSatisfaccionSlider.setDisable(true);
    }
    
    public void modificarTicket() {
        if (modificarTicketButton.getText().equals("Modificar ticket")) {
            ticketTituloField.setDisable(false);
            ticketDescripcionTextArea.setDisable(false);
            modificarTicketButton.setText("Guardar");
        } else {
            ticketTituloField.setDisable(true);
            ticketDescripcionTextArea.setDisable(true);
            modificarTicketButton.setText("Modificar ticket");
            if (ticketTituloField.getText().isBlank() || ticketDescripcionTextArea.getText().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Debes completar título y descripción!");
                alert.show();
            } else {
                Ticket ticketSelectionado = (Ticket) ticketList.getSelectionModel().getSelectedItem();
                ticketSelectionado.setTitulo(ticketTituloField.getText());
                ticketSelectionado.setDescripcion(ticketDescripcionTextArea.getText());
            }
        }
    }
    
    public void evaluarTicket() {
        Ticket ticketSelectionado = (Ticket) ticketList.getSelectionModel().getSelectedItem();
        ticketSelectionado.setSatisfaccion((int) ticketSatisfaccionSlider.getValue());
        ticketSelectionado.setEstado(EstadoTicket.TRATADO);
        mostrarDetalles(ticketSelectionado);
    }
    
    public void eliminarTicket() {
        Ticket ticketSelectionado = (Ticket) ticketList.getSelectionModel().getSelectedItem();
        ticketService.eliminarTicket(ticketSelectionado);
        actualizarTicketList("");
        eliminarDetalles();
    }
    
    public void exportarTicket() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String fecha = LocalDateTime.now().format(formatter);
        String filePath = "informe_" + cliente.getApellido() + "_" + fecha + ".csv";

        Boolean res = CsvUtils.generarReporteCSV(cliente.getTickets(), filePath);
        if (res) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Tickets exportados en archivo SoportePro/" + filePath);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error en la exportación!");
            alert.show();
        }
    }
    
    public void buscarTicket() {
        actualizarTicketList(ticketSearchBar.getText());
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        bienvenidoText.setText("Bienvenido " + cliente.getNombre() + "!");
    }

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    
    private static List<Ticket> filtrarPorTitulo(List<Ticket> lista, String busqueda) {
        if (busqueda == null || busqueda.trim().isEmpty()) {
            return lista;
        }
        String busquedaLower = busqueda.toLowerCase();

        return lista.stream()
                .filter(t -> t.getTitulo().toLowerCase().contains(busquedaLower))
                .collect(Collectors.toList());
    }
}
