/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zyraaclc;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jaymar
 */
public class AddPopupController implements Initializable{

     @FXML JFXTextField document;
    @FXML JFXDatePicker dateAdd;
    FileInputStream inputStream = null;
    File file = null;
    double xOffset,yOffset;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    @FXML public void fileOpen()throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("further.fxml"));
        
        FileChooser chooser = new FileChooser();
        Stage prime = new Stage();
        prime.setScene(new Scene(pane));
        file = chooser.showOpenDialog(prime);
        
    }
    @FXML public void addAction(Event event)throws SQLException, IOException{
        String nameVal = document.getText().trim();
        String datesVal = dateAdd.getValue().toString();
        
        String q = "insert into documents(name,dates) values('"+nameVal+"','"+datesVal+"')";
        
        Connection conn = new connectionDetails().conn();
        PreparedStatement stm = conn.prepareStatement("insert into documents(name, dates,doc,extension) " + "values(?,?,?,?)");
                    stm.setString(1, nameVal);
                    stm.setString(2, datesVal);
            stm.setBlob(3, new FileInputStream(file));
            String ext = file.getName().substring(file.getName().lastIndexOf(".")+1);
            stm.setString(4, ext.toLowerCase());
            if(ext.toLowerCase().equals("docx")){
                stm.executeUpdate();
        int num = stm.getUpdateCount();
        if(num > 0){
            new Alert(Alert.AlertType.INFORMATION, "Success").showAndWait();
            
            AnchorPane p = AdminPortalController.p;
           
              AnchorPane p2 = (AnchorPane)p.getChildren().get(0);
              p2.getChildren().clear();
              AnchorPane paner = FXMLLoader.load(getClass().getResource("home.fxml"));
              p2.getChildren().add(paner);
            
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
           window.close();
           
           
        }else{
            new Alert(Alert.AlertType.ERROR).showAndWait();
        }
            }else new Alert(Alert.AlertType.ERROR,"The file is not a valid word document").show();
        
        
        
        
    }
    @FXML public void close(Event event){
        Stage window = (Stage) ((Node)event.getSource()).getParent().getScene().getWindow();
        window.close();
    }
    
}
