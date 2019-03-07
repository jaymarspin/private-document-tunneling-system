/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zyraaclc;

import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jaymar
 */
public class AddRequestController implements Initializable {
    @FXML Label documentName;
    @FXML JFXDatePicker dateNeeded;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        documentName.setText(HomeController.documentName);
        
        
        
    }    
    
    @FXML public void addRequest(Event e) throws SQLException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String nowDater = sdf.format(now);
        
        Connection c = new connectionDetails().conn();
        String dateNeeded = this.dateNeeded.getValue().toString();
        System.out.println(dateNeeded);
        
        String q = "INSERT INTO requests(document_id,requestorId,rName,rDate,nDate,approved,email) VALUES("+HomeController.masterId+","+FXMLDocumentController.id+",'"+FXMLDocumentController.name+"','"+nowDater+"','"+dateNeeded+"',0,'"+FXMLDocumentController.email+"')";
        Statement stm = c.createStatement();
        stm.executeUpdate(q);
        if(stm.getUpdateCount() > 0){
            Stage window = (Stage) ((Node) e.getSource()).getParent().getParent().getScene().getWindow();
            
            new Alert(Alert.AlertType.INFORMATION,"Success!").show();
            window.close();
        }else{
            System.err.println("Failed!");
        }
    
    }
    
    
    @FXML public void close(Event event){
        Stage window = (Stage) ((Node)event.getSource()).getParent().getScene().getWindow();
        window.close();
    }
    
}
