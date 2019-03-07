/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zyraaclc;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jaymar
 */
public class FurtherController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML AnchorPane mPane;
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        
    }    
    @FXML public void showRequests(Event e)throws IOException{
        
        try{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("requestList.fxml"));
        AnchorPane oldPane = (AnchorPane) ((Node)HomeController.masterResource.getSource()).getParent().getParent().getParent().getParent().getParent();
            
        oldPane.getChildren().clear();
        pane.setPrefSize(oldPane.getWidth(), oldPane.getHeight());
        oldPane.getChildren().add(pane);
        Stage toClose = (Stage) ((Node)e.getSource()).getParent().getScene().getWindow();
        toClose.close();
        }catch(Exception z){
            System.out.println(z.getMessage());
        }
        
    }
     @FXML public void close(Event event){
        Stage window = (Stage) mPane.getScene().getWindow();
        window.close();
    } 
    
}
