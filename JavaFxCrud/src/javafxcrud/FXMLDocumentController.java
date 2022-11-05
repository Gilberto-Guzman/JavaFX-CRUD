/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxcrud;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.TextField;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableRow;

/**
 *
 * @author joseg
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField txtCourse;
    @FXML
    private TextField txtMobile;
    @FXML
    private TextField txtName;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Label label;

    @FXML
    private TableView<Student> table;
    
    @FXML
    private TableColumn<Student, String> IDcolumn;
    @FXML
    private TableColumn <Student, String> Namecolumn;  
    @FXML
    private TableColumn<Student, String> Coursecolumn;
    @FXML
    private TableColumn <Student, String> Mobilecolumn;

    
    public void table()
      {
        Connect();
        ObservableList<Student> students = FXCollections.observableArrayList();
        try
            {
           pst = con.prepareStatement("select id,name,mobile,course from Registration");  
           ResultSet rs = pst.executeQuery();
            {
        while (rs.next())
        {
            Student st = new Student();
            st.setId(rs.getString("id"));
            st.setName(rs.getString("name"));
            st.setMobile(rs.getString("mobile"));
            st.setCourse(rs.getString("course"));
            students.add(st);
       }
    }
            table.setItems(students);
            IDcolumn.setCellValueFactory(f -> f.getValue().idProperty());
            Namecolumn.setCellValueFactory(f -> f.getValue().nameProperty());
            Mobilecolumn.setCellValueFactory(f -> f.getValue().mobileProperty());
            Coursecolumn.setCellValueFactory(f -> f.getValue().courseProperty());
                
              
 
       }
      
       catch (SQLException ex)
       {
           Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
       }
 
    table.setRowFactory( tv -> {
    TableRow<Student> myRow = new TableRow<>();
    myRow.setOnMouseClicked (event ->
     {
        if (event.getClickCount() == 1 && (!myRow.isEmpty()))
        {
            myIndex =  table.getSelectionModel().getSelectedIndex();
            id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
            txtName.setText(table.getItems().get(myIndex).getName());
            txtMobile.setText(table.getItems().get(myIndex).getMobile());
            txtCourse.setText(table.getItems().get(myIndex).getCourse());                       
        }
    });
    return myRow;
});
    
    
    }

    
    @FXML
    void Add(ActionEvent event) {
            String stname,mobile,course;
            stname = txtName.getText();
            mobile = txtMobile.getText();
            course = txtCourse.getText();
        try
        {
            pst = con.prepareStatement("insert into Registration(name,mobile,course)values(?,?,?)");
            pst.setString(1, stname);
            pst.setString(2, mobile);
            pst.setString(3, course);
            pst.executeUpdate();
          
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Student Registration");
 
            alert.setHeaderText("Student Registration");
            alert.setContentText("Record Addedddd!");
 
            alert.showAndWait();

 
            table();
            
            txtName.setText("");
            txtMobile.setText("");
            txtCourse.setText("");
            txtName.requestFocus();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    
    @FXML
    void Update(ActionEvent event) {
                String stname,mobile,course;
        
         myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
          
            stname = txtName.getText();
            mobile = txtMobile.getText();
            course = txtCourse.getText();
        try
        {
            pst = con.prepareStatement("update registration set name = ?,mobile = ? ,course = ? where id = ? ");
            pst.setString(1, stname);
            pst.setString(2, mobile);
            pst.setString(3, course);
             pst.setInt(4, id);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Student Registrationn");
 
    alert.setHeaderText("Student Registration");
    alert.setContentText("Updateddd!");
 
    alert.showAndWait();
                table();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    void Delete(ActionEvent event) {
        myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
                    
 
        try
        {
            pst = con.prepareStatement("delete from registration where id = ? ");
            pst.setInt(1, id);
            pst.executeUpdate();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Student Registrationn");
 
alert.setHeaderText("Student Registration");
alert.setContentText("Deletedd!");
alert.showAndWait();
                  table();
        }
                catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    
    
    Connection con;
    PreparedStatement pst;
    int myIndex;
    int id;
    
     public void Connect()
    {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studcruds","root","CHICHARITOBLUE4");
        } catch (ClassNotFoundException ex) {
          
        } catch (SQLException ex) {
            System.out.println("Error 1");
            ex.printStackTrace();
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Connect();
        table();
    }    
    
}
