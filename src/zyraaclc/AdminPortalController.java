
package zyraaclc;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * FXML Controller class
 *
 * @author Jaymar
 */
public class AdminPortalController implements Initializable {

    
    
    @FXML VBox menuHolder;
    @FXML AnchorPane mPane;
    public static Event masterEvent;
    public static AnchorPane p;
    double xOffset,yOffset;
    @FXML JFXButton notif;
    
    @FXML Label labeler;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
        labeler.setText(FXMLDocumentController.name+" - "+FXMLDocumentController.role);
        if(FXMLDocumentController.role.equals("admin")){
            menuHolder.getChildren().remove(2);
            menuHolder.getChildren().remove(3);
        }else{
            menuHolder.getChildren().remove(3);
            menuHolder.getChildren().remove(1);
        }
        p = mPane;
        mPane.getChildren().clear();
        try {
            loadHome(new Event(EventType.ROOT));
        } catch (IOException ex) {
            Logger.getLogger(AdminPortalController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }   
    
    
    @FXML public void signOut(Event e) throws IOException{
        Stage prime = new Stage();
        AnchorPane p = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        prime.setScene(new Scene(p));
        prime.initStyle(StageStyle.UNDECORATED);
        prime.show();
        Stage window = (Stage) ((Node)e.getSource()).getParent().getParent().getScene().getWindow();
        window.close();
    }
    
    
    
    @FXML public void loadUsers(Event e) throws IOException{
        mPane.getChildren().clear();
        
        AnchorPane p = (AnchorPane) FXMLLoader.load(getClass().getResource("users.fxml"));
        mPane.getChildren().add(p);
        masterEvent = e;
    }
    @FXML public void notif(Event e) throws IOException{
        mPane.getChildren().clear();
        
        AnchorPane p = (AnchorPane) FXMLLoader.load(getClass().getResource("notification.fxml"));
        mPane.getChildren().add(p);
        masterEvent = e;
    }
    
    @FXML public void loadHome(Event e) throws IOException{
        mPane.getChildren().clear();
        AnchorPane home = (AnchorPane) FXMLLoader.load(getClass().getResource("home.fxml"));
            mPane.getChildren().add(home);
            masterEvent = e;
    }
    
    
    @FXML public void reportView(Event e)throws IOException{
        newStage("report.fxml");
            masterEvent = e;
    }
    
    @FXML public void myRequests(Event e)throws IOException{
        mPane.getChildren().clear();
        AnchorPane home = (AnchorPane) FXMLLoader.load(getClass().getResource("myRequests.fxml"));
            mPane.getChildren().add(home);
            masterEvent = e;
    }
 
       public void newStage(String load)throws IOException{
        Stage primary = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(load));
        Scene s = new Scene(root);
        s.setFill(Color.TRANSPARENT);
        primary.setScene(s);
       
        primary.initModality(Modality.APPLICATION_MODAL);
        primary.initStyle(StageStyle.TRANSPARENT);
        
        
        primary.show();
    }
    
   
    
  
    @FXML public void mousePressed(MouseEvent event){
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
    }
    @FXML public void dragged(MouseEvent event){
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setX(event.getScreenX() - xOffset);
            window.setY(event.getScreenY() - yOffset);
    }
    @FXML public void close(MouseEvent event){
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    } 
    

}
  
