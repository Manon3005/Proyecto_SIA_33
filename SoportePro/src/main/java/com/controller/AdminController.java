package com.controller;

import com.model.domain.Empleado;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.util.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class AdminController implements Initializable{
    
    @FXML
    private AnchorPane main_form;
    
    @FXML
    private Button addEmployee_actualizarBtn;

    @FXML
    private Button addEmployee_agregarBtn;

    @FXML
    private TextField addEmployee_apellido;

    @FXML
    private Button addEmployee_borrarBtn;

    @FXML
    private Button addEmployee_btn;

    @FXML
    private Button addEmployee_clearBtn;
 
    @FXML
    private TableColumn<Empleado, String> addEmployee_col_apellido;

    @FXML
    private TableColumn<Empleado, String> addEmployee_col_correo;

    @FXML
    private TableColumn<Empleado, String> addEmployee_col_employeeID;

    @FXML
    private TableColumn<Empleado, String> addEmployee_col_nombre;

    @FXML
    private TableColumn<Empleado, String> addEmployee_col_salario;

    @FXML
    private TextField addEmployee_correo;

    @FXML
    private TextField addEmployee_employeeID;

    @FXML
    private AnchorPane addEmployee_form;

    @FXML
    private TextField addEmployee_nombre;

    @FXML
    private TextField addEmployee_search;

    @FXML
    private Button home_btn;
    @FXML
    private TableView<Empleado> addEmployee_tableView;
    
    @FXML
    private BarChart<?, ?> home_chart;

    @FXML
    private Label home_empleadosConTickets;

    @FXML
    private Label home_empleadosSinTickets;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_totalEmpleados;

    @FXML
    private Button logout;

    @FXML
    private Button salary_actualizarBtn;

    @FXML
    private Label salary_apellido;

    @FXML
    private Button salary_btn;

    @FXML
    private Button salary_clearBtn;

    @FXML
    private TableColumn<Empleado, String> salary_col_apellido;

    @FXML
    private TableColumn<Empleado, String> salary_col_correo;

    @FXML
    private TableColumn<Empleado, String> salary_col_employeeID;

    @FXML
    private TableColumn<Empleado, String> salary_col_nombre;

    @FXML
    private TableColumn<Empleado, Double> salary_col_salario;

    @FXML
    private Label salary_correo;

    @FXML
    private TextField salary_employeeID;

    @FXML
    private AnchorPane salary_form;

    @FXML
    private Label salary_nombre;

    @FXML
    private TextField salary_salario;

    @FXML
    private TableView<Empleado> salary_tableView;

    @FXML
    private Label username;
    
    private double x=0;
    private double y= 0;
    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet resultado;
    
    
    public void homeTotalEmployees() throws SQLException{
        String sql = "SELECT COUNT(rut)  AS total FROM empleado";
        
        connect = DBConnection.getConnection();
        int countData = 0;
        try{
            
            prepare = connect.prepareStatement(sql);
            resultado = prepare.executeQuery();
            
            while(resultado.next()){
                countData = resultado.getInt("total");
            }
            
            home_totalEmpleados.setText(String.valueOf(countData));
            
            
        }catch(SQLException e){}
    }
    
    public void homeTotalTicketsAsignados() throws SQLException{
        
        String sql = "SELECT COUNT(*) AS total FROM ticket WHERE empleado_rut IS NOT  NULL";
        
        connect = DBConnection.getConnection();
        int countData = 0;
        try{
            
            prepare = connect.prepareStatement(sql);
            resultado = prepare.executeQuery();
            
            while(resultado.next()){
                countData = resultado.getInt("total");
            }
            
            home_empleadosConTickets.setText(String.valueOf(countData));
            
            
        }catch(SQLException e){}
    }
   
    public void homeTotalTicketsSinAsignar() throws SQLException{
        
        String sql = "SELECT COUNT(*) AS total FROM ticket WHERE empleado_rut IS NULL";
        
        connect = DBConnection.getConnection();
        int countData = 0;
        try{
            
            prepare = connect.prepareStatement(sql);
            resultado = prepare.executeQuery();
            
            while(resultado.next()){
                countData = resultado.getInt("total");
            }
            
            home_empleadosSinTickets.setText(String.valueOf(countData));
            
            
        }catch(SQLException e){e.printStackTrace();}
    }
    
    
    
    public ObservableList<Empleado> addEmployeeListData() throws SQLException{
        ObservableList<Empleado> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM empleado";
        
        connect = DBConnection.getConnection();
       try{
            prepare = connect.prepareStatement(sql);
            resultado = prepare.executeQuery();
            Empleado empleado;
            
            while(resultado.next()){
                empleado = new Empleado(resultado.getString("rut"),
                        resultado.getString("contrasena") ,
                        resultado.getString("nombre"),
                        resultado.getString("apellido"),
                        resultado.getString("correo"),
                        resultado.getDouble("salario"));
                lista.add(empleado);
            }
        }catch(SQLException e){}
       return lista;
    }
    
    private ObservableList<Empleado> addEmployeeList;
    
    
    public void addEmployeeShowListData() throws SQLException{
        addEmployeeList = addEmployeeListData();
        
        addEmployee_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("rut"));
        addEmployee_col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        addEmployee_col_apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        addEmployee_col_correo.setCellValueFactory(new PropertyValueFactory<>("correo"));
  
        addEmployee_tableView.setItems(addEmployeeList);
    }
    
    public void addEmployeeAdd() throws SQLException{      
        String sql = "INSERT INTO empleado(rut,contrasena,nombre,apellido,correo,salario) VALUES(?,?,?,?,?,?)";
        
        connect = DBConnection.getConnection();
        
        try{
            Alert alert;
            if(addEmployee_employeeID.getText().isEmpty() || addEmployee_nombre.getText().isEmpty()
                    ||addEmployee_apellido.getText().isEmpty() || addEmployee_correo.getText().isEmpty()){
                
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("por favor, complete todos los campos en blanco");
                alert.showAndWait();
            }else{
                
                String check = "SELECT rut FROM empleado WHERE rut = '"+addEmployee_employeeID.getText()+"'";
                
                statement = connect.createStatement();
                resultado = statement.executeQuery(check);
                
                if(resultado.next()){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Rut: "+addEmployee_employeeID.getText()+" ya existía ");
                    alert.showAndWait();
                }else{
                    
                
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addEmployee_employeeID.getText());
                    prepare.setString(2,"pass");
                    prepare.setString(3, addEmployee_nombre.getText());
                    prepare.setString(4, addEmployee_apellido.getText());
                    prepare.setString(5, addEmployee_correo.getText());
                    prepare.setDouble(6,0.0);
                    prepare.executeUpdate();
                     
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("agregado con éxito");
                    alert.showAndWait();
                    
                  
                    addEmployeeShowListData();
                    addEmployeeReset();
                }

            }
        
        }catch(SQLException e){ e.printStackTrace();}
    }
    
    public void addEmployeeReset(){
        addEmployee_employeeID.setText("");
        addEmployee_nombre.setText("");
        addEmployee_apellido.setText("");
        addEmployee_correo.setText("");
        
        
    }
     
     public void salarySelect(){
        
        Empleado empleado = salary_tableView.getSelectionModel().getSelectedItem();
        int num = salary_tableView.getSelectionModel().getSelectedIndex();
        
        if((num -1) <-1){
            return;
        }
        
        salary_employeeID.setText(empleado.getRut());
        salary_nombre.setText(empleado.getNombre());
        salary_apellido.setText(empleado.getApellido());
        salary_correo.setText(empleado.getCorreo());
        salary_salario.setText(String.valueOf(empleado.getSalario()));
        
    }
    
    
    public void addEmployeeSelect(){
        Empleado empleado = addEmployee_tableView.getSelectionModel().getSelectedItem();
        int num = addEmployee_tableView.getSelectionModel().getSelectedIndex();
        
        if((num-1)<-1){return;}
        
        addEmployee_employeeID.setText(empleado.getRut());
        addEmployee_nombre.setText(empleado.getNombre());
        addEmployee_apellido.setText(empleado.getApellido());
        addEmployee_correo.setText(empleado.getCorreo());
        
    }
    
    public void addEmployeeUpdate() throws SQLException{
              
        String sql = "UPDATE empleado SET nombre = '"+addEmployee_nombre.getText()+"', apellido = '"
                + addEmployee_apellido.getText()+"', correo = '"+addEmployee_correo.getText()+"' WHERE rut = '" +addEmployee_employeeID.getText()+"'";
        
        connect = DBConnection.getConnection();
        
        try{
            Alert alert;
            if(addEmployee_employeeID.getText().isEmpty() || addEmployee_nombre.getText().isEmpty()
                    ||addEmployee_apellido.getText().isEmpty() || addEmployee_correo.getText().isEmpty()){
                
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("por favor, complete todos los campos en blanco");
                alert.showAndWait();
            
            }else{
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("¿Estás seguro de que quieres actualizar al Empleado con rut:"+addEmployee_employeeID.getText()+"?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("actualizado con éxito");
                    alert.showAndWait();
                    
                    addEmployeeShowListData();
                    addEmployeeReset();
                }
                
            }    
        }catch(SQLException e){}   
    }
    
    
    public void salaryUpdate() throws SQLException{
        
        String sql = "UPDATE empleado SET salario = '"+salary_salario.getText()+
                "' WHERE rut = '"+salary_employeeID.getText()+"'";
        
        
        connect = DBConnection.getConnection();
        
        try{
            Alert alert;
            
           if(salary_employeeID.getText().isEmpty()
                   ||salary_nombre.getText().isEmpty()
                   ||salary_apellido.getText().isEmpty()
                   || salary_correo.getText().isEmpty()){
           
               alert = new Alert(AlertType.ERROR);
               alert.setTitle("Error Message");
               alert.setHeaderText(null);
               alert.setContentText("por favor, seleccione primero un artículo");
               alert.showAndWait(); 
            }else{
               statement = connect.createStatement();
               statement.executeUpdate(sql);
               
               alert = new Alert(AlertType.INFORMATION);
               alert.setTitle("Information Message");
               alert.setHeaderText(null);
               alert.setContentText("actualizado con éxito");
               alert.showAndWait(); 
               
               salaryShowListData();
            }
        }catch(SQLException e){}            
    }
    
    public void salaryReset(){
        salary_employeeID.setText("");
        salary_nombre.setText("");
        salary_apellido.setText("");
        salary_correo.setText("");
        salary_salario.setText("");
    }
    
    public void addEmployeeSearch(){
        
        FilteredList<Empleado> filter = new FilteredList<>(addEmployeeList,e-> true);
        
        addEmployee_search.textProperty().addListener((Observable,oldValue,newValue)->{
        
            filter.setPredicate(predicateEmpleado ->{
                
                if(newValue == null ||  newValue.isEmpty()){
                   return true; 
                }
                
                String searchKey = newValue.toLowerCase();
                
                if(predicateEmpleado.getRut().contains(searchKey)){
                    return true;
                }else if(predicateEmpleado.getNombre().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateEmpleado.getApellido().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateEmpleado.getCorreo().toLowerCase().contains(searchKey)){
                    return true;
                }else return false;
            });
        });
        
       SortedList<Empleado> sortList = new SortedList<>(filter);
       
       sortList.comparatorProperty().bind(addEmployee_tableView.comparatorProperty());
       addEmployee_tableView.setItems(sortList);
    }
    
    
    
    public void addEmployeeDelete() throws SQLException{
        String sql = "DELETE FROM empleado WHERE rut = '"+addEmployee_employeeID.getText()+"'";
        
        connect = DBConnection.getConnection();
        
        try{
            
            Alert alert;
            if(addEmployee_employeeID.getText().isEmpty() || addEmployee_nombre.getText().isEmpty()
                    ||addEmployee_apellido.getText().isEmpty() || addEmployee_correo.getText().isEmpty()){
                
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("por favor, complete todos los campos en blanco");
                alert.showAndWait();
            
            }else{
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("¿Estás seguro de que quieres borrar al Empleado con rut:"+addEmployee_employeeID.getText()+"?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    
                    String deleteInfo = "DELETE FROM empleado WHERE rut = '"+addEmployee_employeeID.getText()+"'";
                    
                    prepare = connect.prepareStatement(deleteInfo);
                    prepare.executeUpdate();
                    
                    
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Borrado con éxito");
                    alert.showAndWait();
                    
                    addEmployeeShowListData();
                    addEmployeeReset();
                }
            }
            
            
        
        }catch(SQLException e){}
    
    }
    
    public void homeChart() throws SQLException{
        
        home_chart.getData().clear();
        
        String sql ="SELECT date,COUNT(id) FROM empleado GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 7";
        connect = DBConnection.getConnection();
        try{
            
            XYChart.Series chart = new XYChart.Series();
            
            prepare = connect.prepareStatement(sql);
            resultado = prepare.executeQuery();
            
            while(resultado.next()){
                chart.getData().add(new XYChart.Data(resultado.getString(1),resultado.getInt(2)));
                
            }
            
            home_chart.getData().add(chart);
                    
        }catch(SQLException e){}
        
        
    }
    
    public void displayUsername(String nombre){
        username.setText("Bienvenido " + nombre + "!");
    }
    
    public ObservableList<Empleado> salaryListData() throws SQLException{
       
        ObservableList<Empleado> listData = FXCollections.observableArrayList();
        
        String sql = "SELECT rut,nombre,apellido,correo,salario FROM empleado";
        
        connect = DBConnection.getConnection();
        
        try{
            prepare = connect.prepareStatement(sql);
            resultado = prepare.executeQuery();
            
            Empleado empleado;
            
            while(resultado.next()){
                empleado = new Empleado(resultado.getString("rut"),"pass",resultado.getString("nombre"),resultado.getString("apellido"), resultado.getString("correo"), resultado.getDouble("salario"));
                listData.add(empleado);
            }
        
        }catch(SQLException e){}
        
        return listData;
        
    }
    
    private ObservableList<Empleado> salaryList;
    
    public void salaryShowListData() throws SQLException{
        salaryList  = salaryListData();
        
        salary_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("rut"));
        salary_col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        salary_col_apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        salary_col_correo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        salary_col_salario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        
        salary_tableView.setItems(salaryList);
        
    }
    
     
    
    public void switchForm(ActionEvent event) throws SQLException{
        
        if(event.getSource()== home_btn){
            home_form.setVisible(true);
            addEmployee_form.setVisible(false);
            salary_form.setVisible(false);
            
            homeTotalEmployees();
            homeTotalTicketsAsignados();
            homeTotalTicketsSinAsignar();
            homeChart();
            
        }else if(event.getSource()== addEmployee_btn){
            home_form.setVisible(false);
            addEmployee_form.setVisible(true);
            salary_form.setVisible(false);
            addEmployeeShowListData();
            addEmployeeSearch();
            
        }else if(event.getSource()== salary_btn){
            home_form.setVisible(false);
            addEmployee_form.setVisible(false);
            salary_form.setVisible(true);
            
            salaryShowListData();
        }
    }
   
    public void logout(){
       Alert alert = new Alert(AlertType.CONFIRMATION);
       alert.setTitle("Mensaje de confirmacion");
       alert.setHeaderText(null);
       alert.setContentText("¿Estás seguro de que quieres cerrar sesión?");
       Optional<ButtonType> option = alert.showAndWait();
       
      try{
        if(option.get().equals(ButtonType.OK)){
            
            logout.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("/com/view/LoginPage.fxml"));
            Stage stage = (Stage) logout.getScene().getWindow();
            
            root.setOnMousePressed((MouseEvent event) ->{
                x= event.getSceneX();
                y= event.getSceneY();   
            });
            
            root.setOnMouseDragged((MouseEvent event) ->{
               stage.setX(event.getScreenX() - x);
               stage.setY(event.getScreenX() - y);
               
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {    
            
            homeTotalEmployees();
            homeTotalTicketsAsignados();
            homeTotalTicketsSinAsignar();
            homeChart();
            
            addEmployeeShowListData();
            salaryShowListData();
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
     
}
