package com.controller;

import com.model.domain.Cliente;
import com.model.domain.Empleado;
import com.model.domain.EstadoTicket;
import com.model.domain.Ticket;
import com.model.services.ClienteService;
import com.model.services.EmpleadoService;
import com.model.services.TicketService;
import com.view.App;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author manon
 */
public class EmpleadoController implements Initializable {
    
    @FXML
    Text bienvenidoText;
    
    @FXML
    ListView ticketEnProcesoList;
    
    @FXML
    ListView ticketHistoricoList;
    
    @FXML
    ListView ticketPendienteList;
    
    @FXML
    TextField ticketTituloField;
    
    @FXML
    TextField ticketClienteField;
    
    @FXML
    TextArea ticketDescripcionTextArea;
    
    @FXML
    Text estadoText;
    
    @FXML
    Button desconectarseButton;
    
    @FXML
    Slider ticketSatisfaccionSlider;
    
    @FXML
    Label ticketSatisfaccionLabel;
    
    @FXML
    Button modificarTicketButton;

    private Empleado empleado;
    private ClienteService clienteService;
    private EmpleadoService empleadoService;
    private TicketService ticketService;
    private Ticket ticketVisualizado = null;
    
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
        bienvenidoText.setText("Bienvenido " + empleado.getNombre() + "!");
    }

    public void setEmpleadoService(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    
    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        ticketEnProcesoList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ticketEnProcesoList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ticketVisualizado = (Ticket) newSelection;
                ticketHistoricoList.getSelectionModel().clearSelection();
                ticketPendienteList.getSelectionModel().clearSelection();
                mostrarDetalles();
            }
        });
        
        ticketHistoricoList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ticketHistoricoList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ticketVisualizado = (Ticket) newSelection;
                ticketEnProcesoList.getSelectionModel().clearSelection();
                ticketPendienteList.getSelectionModel().clearSelection();
                mostrarDetalles();
            }
        });
        
        ticketPendienteList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ticketPendienteList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ticketEnProcesoList.getSelectionModel().clearSelection();
                ticketHistoricoList.getSelectionModel().clearSelection();
                ticketVisualizado = (Ticket) newSelection;
                mostrarDetalles();
            }
        });
        
        ticketTituloField.setDisable(true);
        ticketClienteField.setDisable(true);
        ticketDescripcionTextArea.setDisable(true);
        
        ticketSatisfaccionSlider.setMin(1);
        ticketSatisfaccionSlider.setMax(5);
        ticketSatisfaccionSlider.setValue(5);
        ticketSatisfaccionSlider.setShowTickMarks(true);
        ticketSatisfaccionSlider.setShowTickLabels(true);
        ticketSatisfaccionSlider.setMajorTickUnit(1);
        ticketSatisfaccionSlider.setMinorTickCount(0); 
        ticketSatisfaccionSlider.setSnapToTicks(true); 
    } 
    
    public void mostrarDetalles() {
        Cliente cliente = clienteService.buscarPorRut(ticketVisualizado.getClienteRut());
        
        ticketClienteField.setText(cliente.getNombre() + " " + cliente.getApellido());
        ticketTituloField.setText(ticketVisualizado.getTitulo());
        ticketDescripcionTextArea.setText(ticketVisualizado.getDescripcion());
        estadoText.setText(ticketVisualizado.getEstado().toString());
        
        if (ticketVisualizado.getSatisfaccion() != 0) {
            ticketSatisfaccionSlider.setValue(ticketVisualizado.getSatisfaccion());
            ticketSatisfaccionSlider.setVisible(true);
            ticketSatisfaccionLabel.setVisible(true);
        } else {
            ticketSatisfaccionSlider.setVisible(false);
            ticketSatisfaccionLabel.setVisible(false);
        }
        
        EstadoTicket estado = ticketVisualizado.getEstado();
        
        if (null == estado) {
            modificarTicketButton.setVisible(false);
        } else switch (estado) {
            case PENDIENTE:
                modificarTicketButton.setVisible(true);
                modificarTicketButton.setText("Cargarse de este ticket");
                break;
            case EN_PROCESO:
                modificarTicketButton.setVisible(true);
                modificarTicketButton.setText("Marcar este ticket como tratado");
                break;
            default:
                modificarTicketButton.setVisible(false);
                break;
        }
    }
    
    public void actualizarTicketLists() {
        eliminarDetalles();
        ticketEnProcesoList.setItems(FXCollections.observableArrayList());
        ticketHistoricoList.setItems(FXCollections.observableArrayList());
        ticketPendienteList.setItems(FXCollections.observableArrayList());
                
        List<Ticket> ticketEmpleadoList = empleado.getTickets();
        for (Ticket ticket: ticketEmpleadoList) {
            EstadoTicket estado = ticket.getEstado();
            if (estado == EstadoTicket.EN_PROCESO) {
                ticketEnProcesoList.getItems().add(ticket);
            } else {
                ticketHistoricoList.getItems().add(ticket);
            }
        }
        
        for (Ticket ticket: ticketService.recuperarPendienteTicketList()) {
            ticketPendienteList.getItems().add(ticket);
        }
    }
    
    public void modificarTicket() {
        if (modificarTicketButton.getText().equals("Cargarse de este ticket")) {
            empleadoService.cargarseTicket(empleado, ticketVisualizado);
        } else {
            empleadoService.terminarTicket(ticketVisualizado);
        }
        actualizarTicketLists();
    }
    
    public void eliminarDetalles() {
        ticketTituloField.clear();
        ticketDescripcionTextArea.clear();
        estadoText.setText("");
        ticketClienteField.clear();
    }
    
    public void desconectarse() {
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setTitle("Mensaje de confirmacion");
       alert.setHeaderText(null);
       alert.setContentText("¿Estás seguro de que quieres cerrar sesión?");
       Optional<ButtonType> option = alert.showAndWait();
       
       if(option.get().equals(ButtonType.OK)){
            Stage stage = (Stage) desconectarseButton.getScene().getWindow();
            App.logout(stage);
        }
    }
}
