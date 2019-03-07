/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zyraaclc;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Jaymar
 */
public class UsersController implements Initializable {

    @FXML TableView<globalBean> usersTable;
    
    TableColumn<globalBean, Integer> idCol = new TableColumn<>("id");
    TableColumn<globalBean, String> fnameCol = new TableColumn<>("First Name");
    TableColumn<globalBean, String> lnameCol = new TableColumn<>("Last Name");
    TableColumn<globalBean, String> usernameCol = new TableColumn<>("Username");
    TableColumn<globalBean, String> passwordCol = new TableColumn<>("Password");
    TableColumn<globalBean, HBox> emailCol = new TableColumn<>("Email");
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        fnameCol.setCellValueFactory(new PropertyValueFactory<>("fname"));
        lnameCol.setCellValueFactory(new PropertyValueFactory<>("lname"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        usersTable.getColumns().addAll(fnameCol,lnameCol,usernameCol,passwordCol,emailCol);
        try {
            tableLoad();
        } catch (SQLException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    public void tableLoad() throws SQLException{
        Connection c = new connectionDetails().conn();
        String q = "SELECT * FROM users ORDER BY id DESC";
        ObservableList<globalBean> apple = FXCollections.observableArrayList();
        Statement stm = c.createStatement();
        stm.executeQuery(q);
        ResultSet set = stm.getResultSet();
        
        while (set.next()) {            
            apple.add(new globalBean(set.getInt("id"), set.getString("fname"), set.getString("lname"), set.getString("username"), set.getString("password"),set.getString("email")));
        }
        usersTable.setItems(apple);
    }
    
    
    @FXML public void addUsers(Event e) throws IOException{
        Stage primary = new Stage();
        primary.initStyle(StageStyle.UNDECORATED);
        AnchorPane pane = FXMLLoader.load(getClass().getResource("addUser.fxml"));
        Scene s = new Scene(pane);
        primary.setScene(s);
        primary.sizeToScene();
        primary.initModality(Modality.APPLICATION_MODAL);
        Stage toClose = (Stage) ((Node)e.getSource()).getScene().getWindow();
        primary.show();
    }
    
}
