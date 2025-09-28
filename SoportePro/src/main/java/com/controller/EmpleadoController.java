/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.controller;

import com.model.domain.Cliente;
import com.model.services.TicketService;
import com.model.domain.Empleado;
import com.model.domain.Ticket;
import com.util.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * FXML Controller class
 *
 * @author manon
 */
public class EmpleadoController implements Initializable {

    @FXML
    private Button Empleado_actualizarBtn;

    @FXML
    private Button Empleado_clearBtn;
       
    @FXML
    private Label empleado_ID;

    @FXML
    private Button buscarTicketButton;

    @FXML
    private Label empleado_apellido;

    @FXML
    private Text empleado_bienvenidoText;

    @FXML
    private Label empleado_correo;

    @FXML
    private TextField empleado_estado;

    @FXML
    private Label empleado_nombre;

    @FXML
    private Text estadoText;

    @FXML
    private Button logout_empleado;

    @FXML
    private TextArea ticketDescripcionTextArea;

    @FXML
    private TextField ticketSearchBar;

    @FXML
    private TableColumn<Ticket, String> ticket_col_ID;

    @FXML
    private TableColumn<Ticket, String> ticket_col_estado;

    @FXML
    private TableColumn<Ticket, String> ticket_col_satisfaccion;

    @FXML
    private TableColumn<Ticket, String> ticket_col_titulo;

    @FXML
    private TableView<Ticket> ticket_tableView;

    
    private double x=0;
    private double y= 0;
    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet resultado;
    
    public void TicketReset(){
        empleado_nombre.setText("");
        empleado_apellido.setText("");
        empleado_correo.setText("");
        empleado_estado.setText("");
    }
    
    public ObservableList<Ticket> ticketListData() throws SQLException{
       
        ObservableList<Ticket> listData = FXCollections.observableArrayList();
        
        String sql = "SELECT id,titulo,descripcion,cliente_rut FROM ticket";
        
        connect = DBConnection.getConnection();
        
        try{
            prepare = connect.prepareStatement(sql);
            resultado = prepare.executeQuery();
            
            Ticket ticket;
            
            while(resultado.next()){
                ticket = new Ticket(resultado.getInt("id"),resultado.getString("titulo"),resultado.getString("descripcion"), resultado.getString("cliente_rut"));
                listData.add(ticket);
            }
        
        }catch(SQLException e){}
        
        return listData;
        
    }
    
    private ObservableList<Ticket> ticketList;
    
    public void ticketShowListData() throws SQLException{
        ticketList  = ticketListData();
        
        ticket_col_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ticket_col_titulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        ticket_col_estado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        ticket_col_satisfaccion.setCellValueFactory(new PropertyValueFactory<>("satisfaccion"));
        
        ticket_tableView.setItems(ticketList);
        
    }
    public void ticketSelect(){
        
        Ticket ticket = ticket_tableView.getSelectionModel().getSelectedItem();
    int num = ticket_tableView.getSelectionModel().getSelectedIndex();

    if (ticket == null || num < 0) {
        return;
    }

    try {
        
        connect = DBConnection.getConnection();

        String sql = "SELECT * FROM cliente c "
                   + "INNER JOIN ticket t ON c.rut = t.cliente_rut "
                   + "WHERE t.id = ?";
        prepare = connect.prepareStatement(sql);
        prepare.setInt(1, ticket.getId()); 
        resultado = prepare.executeQuery();

        Cliente cliente = null;
        if (resultado.next()) {
            cliente = new Cliente();
            cliente.setNombre(resultado.getString("nombre"));
            cliente.setApellido(resultado.getString("apellido"));
            cliente.setCorreo(resultado.getString("correo"));
        }
        empleado_ID.setText(String.valueOf(ticket.getId()));
        ticketDescripcionTextArea.setText(ticket.getDescripcion());
        
        if (cliente != null) {
            empleado_nombre.setText(cliente.getNombre());
            empleado_apellido.setText(cliente.getApellido());
            empleado_correo.setText(cliente.getCorreo());
        } else {
            empleado_nombre.setText("-");
            empleado_apellido.setText("-");
            empleado_correo.setText("-");
        }
        empleado_estado.setText(ticket.getEstado().toString());

    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    }
    
    
    
    public void ticketUpdate() throws SQLException{
        
        String sql = "UPDATE ticket SET estado = '"+empleado_estado.getText()+
                "' WHERE id = '"+empleado_ID.getText()+"'";
        
        
        connect = DBConnection.getConnection();
        
        try{
            Alert alert;
            
           if(empleado_ID.getText().isEmpty()
                   ||empleado_nombre.getText().isEmpty()
                   ||empleado_apellido.getText().isEmpty()
                   || empleado_correo.getText().isEmpty()){
           
               alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Error Message");
               alert.setHeaderText(null);
               alert.setContentText("por favor, seleccione primero un artículo");
               alert.showAndWait(); 
            }else{
               statement = connect.createStatement();
               statement.executeUpdate(sql);
               
               alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Information Message");
               alert.setHeaderText(null);
               alert.setContentText("actualizado con éxito");
               alert.showAndWait(); 
               
               ticketShowListData();
            }
        }catch(SQLException e){}            
    }
    
    public void logout(){
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setTitle("Mensaje de confirmacion");
       alert.setHeaderText(null);
       alert.setContentText("¿Estás seguro de que quieres cerrar sesión?");
       Optional<ButtonType> option = alert.showAndWait();
       
      try{
        if(option.get().equals(ButtonType.OK)){
            
            logout_empleado.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("/com/view/LoginPage.fxml"));
            Stage stage = (Stage) logout_empleado.getScene().getWindow();
            
            root.setOnMousePressed((MouseEvent event) ->{
                x = event.getSceneX();
                y = event.getSceneY();   
            });
            
            root.setOnMouseDragged((MouseEvent event) ->{
               stage.setX(event.getScreenX() - x);
               stage.setY(event.getScreenY() - y);
               
               stage.setOpacity(.8);
            });
            
            root.setOnMouseReleased((MouseEvent event) ->{
                stage.setOpacity(1);
            });
            
            
            stage.setScene(new Scene(root));
            stage.show();
         
        }   
      }catch(IOException e){}  
    }
    
    public void initialize(URL url, ResourceBundle rb) {
        try {    
            ticketShowListData();
            
        } catch(SQLException ex){
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }      
}
