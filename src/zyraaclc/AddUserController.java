/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zyraaclc;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jaymar
 */
public class AddUserController implements Initializable {

    @FXML JFXTextField fname;
    @FXML JFXTextField lname;
    @FXML JFXTextField username;
    @FXML JFXTextField password;
    @FXML JFXTextField email;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML public void addUser(Event e) throws SQLException,IOException{
        String fnameVal = fname.getText().trim();
        String lnameVal = lname.getText().trim();
        String usernameVal = username.getText().trim();
        String passwordVal = password.getText().trim();
        String emailVal = email.getText().trim();
        String q = "INSERT INTO users(fname,lname,username,password,role,email) VALUES('"+fnameVal+"','"+lnameVal+"','"+usernameVal+"','"+passwordVal+"','user','"+emailVal+"')";
        Connection c = new connectionDetails().conn();
        Statement stm = c.createStatement();
        stm.executeUpdate(q);
        if(stm.getUpdateCount() > 0){
            
               
              AnchorPane p = (AnchorPane) ((Node) AdminPortalController.masterEvent.getSource()).getParent().getParent();
              AnchorPane p2 = (AnchorPane)p.getChildren().get(0);
              p2.getChildren().clear();
              AnchorPane paner = FXMLLoader.load(getClass().getResource("users.fxml"));
              p2.getChildren().add(paner);
              Stage window = (Stage) ((Node)e.getSource()).getParent().getParent().getScene().getWindow();
              window.close();
              
               
        }else{
            new Alert(Alert.AlertType.ERROR,"Error Occured!").showAndWait();
        }
        
        
    }
    @FXML public void close(Event event){
        Stage window = (Stage) ((Node)event.getSource()).getParent().getScene().getWindow();
        window.close();
    }
    
}
