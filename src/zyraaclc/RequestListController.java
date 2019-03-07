
package zyraaclc;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;





public class RequestListController implements Initializable {
    
    @FXML TableView<globalBean> requestTable;
    TableColumn<globalBean, Integer> idCol = new TableColumn<>("id");
    TableColumn<globalBean, String> nameCol = new TableColumn<>("Requestor");
    TableColumn<globalBean, String> rDateCol = new TableColumn<>("Request Date");
    TableColumn<globalBean, String> nDateCol = new TableColumn<>("Needed Date");
    TableColumn<globalBean, HBox> actionsCol = new TableColumn<>("Action");
    
    
    @FXML TextField searchVal;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        requestTable.getItems().clear();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("requestorName"));
        rDateCol.setCellValueFactory(new PropertyValueFactory<>("rdate"));
        nDateCol.setCellValueFactory(new PropertyValueFactory<>("ndate"));
        actionsCol.setCellValueFactory(new PropertyValueFactory<>("actions"));
        
        
        requestTable.getColumns().addAll(nameCol,rDateCol,nDateCol,actionsCol);
        try {
            tableLoad("SELECT * FROM requests WHERE document_id = "+HomeController.masterId+" ORDER BY id DESC");
        } catch (SQLException ex) {
            Logger.getLogger(RequestListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    
    @FXML public void search() throws SQLException{
        String val = searchVal.getText().trim();
        
    }
    
    public void tableLoad(String q) throws SQLException{
        ObservableList<globalBean> apple = FXCollections.observableArrayList();
        Connection c = new connectionDetails().conn();
       
        Statement stm = c.createStatement();
        stm.executeQuery(q);
        ResultSet set = stm.getResultSet();
        int count = 0;
        while (set.next()) {   
            count += 1;
            System.out.println(set.getString("nDate"));
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CHECK_CIRCLE);
            icon.setFill(Color.WHITESMOKE);
            JFXButton approveButton  = new JFXButton();
            approveButton.setGraphic(icon);
            approveButton.setId(set.getInt("id")+"");
            FontAwesomeIconView icon2 = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
            icon2.setFill(Color.WHITESMOKE);
            JFXButton disButton  = new JFXButton();
            disButton.setGraphic(icon2);
            disButton.setId(set.getInt("id")+"");
            HBox h = new HBox(3);
            h.setAlignment(Pos.CENTER);
            Label status = new Label("Approved");
            status.setStyle("-fx-text-fill: #458B74");
            approveButton.setStyle("-fx-background-color: #458B74;-fx-text-fill: #f5f5f5");
            disButton.setStyle("-fx-background-color: #CD3333;-fx-text-fill: #f5f5f5");
            Label statusLabel = new Label("Approved");
            statusLabel.setStyle("-fx-text-fill: #458B74");
            if(set.getInt("approved") == 0)h.getChildren().addAll(approveButton,disButton);
            else h.getChildren().add(statusLabel);
            
            String rdate = set.getString("rDate");
            String ndate = set.getString("nDate");
            String email = set.getString("email");
            System.out.println(email);
            apple.add(new globalBean(0, set.getString("rName"), set.getString("rDate"),set.getString("nDate"), h));
            
            approveButton.setOnAction(e ->{
                   int id = Integer.parseInt(((JFXButton)e.getSource()).getId());
                   
                   String q1 = "UPDATE requests SET approved = 1 WHERE id = "+id+"";
                try {
                    Statement stm1 = c.createStatement();
                    stm1.executeUpdate(q1);
                    AnchorPane paner = AdminPortalController.p;
                    AnchorPane n = FXMLLoader.load(getClass().getResource("requestList.fxml"));
                    paner.getChildren().clear();
                    paner.getChildren().add(n);
                    if(stm1.getUpdateCount() > 0){
                            try{
            String host ="smtp.gmail.com" ;
            String user = "kerrwarren927@gmail.com";
            String pass = "kerr092712";
            String to = email;
            String from = "kerrwarren927@gmail.com";
            String subject = "This is confirmation number for your expertprogramming account. Please insert this number to activate your account.";
            String messageText = "your document has been approved with requested date "+rdate+" and needed date "+ndate;
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject); msg.setSentDate(new Date());
            msg.setText(messageText);

           Transport transport=mailSession.getTransport("smtp");
           transport.connect(host, user, pass);
           transport.sendMessage(msg, msg.getAllRecipients());
           transport.close();
           System.out.println("message send successfully");
        }catch(Exception ex)
        {
            new Alert(Alert.AlertType.ERROR,"HTTP Requests Error").showAndWait();
        }
                           new Alert(Alert.AlertType.INFORMATION,"Approved").showAndWait();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(RequestListController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(RequestListController.class.getName()).log(Level.SEVERE, null, ex);
                }
                   
        
        });
            
        }
        if(count > 0){
            requestTable.setItems(apple);
        }else{
            
        }
        
    }
    
    
  
    
}
