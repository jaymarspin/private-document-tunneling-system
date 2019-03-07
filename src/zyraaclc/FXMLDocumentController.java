
package zyraaclc;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FXMLDocumentController implements Initializable {
    
    
    @FXML Label dater;
   @FXML JFXTextField username;
    @FXML JFXPasswordField password;
    public static String role;
    public static int id;
    public static String name;
    public static String email;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dater.setVisible(false);
       
    }  
    
     
    
   @FXML public void newStage(Event e) throws IOException{
       
        Stage primary = new Stage();
        primary.initStyle(StageStyle.UNDECORATED);
        AnchorPane pane = FXMLLoader.load(getClass().getResource("adminPortal.fxml"));
        Scene s = new Scene(pane);
        primary.setScene(s);
        primary.sizeToScene();
         
        Stage toClose = (Stage) ((Node)e.getSource()).getScene().getWindow();
        toClose.close();
        primary.show();
    }
    
    
    @FXML public void loggy(Event z) throws SQLException, IOException{
       try{ 
        Date d = new Date();
        Date d2 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try{
            d2 = sdf.parse(dater.getText().trim());
        }catch(Exception e){
            
        }
        if(d.getTime() < d2.getTime()){
            String userVal = username.getText().trim();
        String passVal = password.getText().trim();
        String q = "SELECT * FROM users WHERE username = '"+userVal+"' && password = '"+passVal+"'";
        Connection c = new connectionDetails().conn();
        Statement stm = c.createStatement();
        stm.executeQuery(q);
        ResultSet set = stm.getResultSet();
        
        int num = 0;
        while (set.next()) {            
            num += 1;
            role = set.getString("role");
            id = set.getInt("id");
            name = set.getString("fname")+" "+set.getString("lname");
            email = set.getString("email");
        }
        if(num > 0)newStage(z);
        else new Alert(Alert.AlertType.ERROR,"Log in invalid!").showAndWait();
        }else{
            
        }
       }catch(Exception zz){
           System.out.println(zz.getMessage());
       }
    }
    
    
     @FXML public void close(Event event){
        Stage window = (Stage) ((Node)event.getSource()).getParent().getScene().getWindow();
        window.close();
    }
    
 
    
}
