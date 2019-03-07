/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zyraaclc;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat; 
import java.util.Date;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Jaymar
 */
public class HomeController implements Initializable {

    public static Event masterResource;
    public static int masterId;
    public static String documentName;
    @FXML TextField searVal;
    
    connectionDetails stash = new connectionDetails();
    double xOffset,yOffset;
    @FXML TableView<beany> documentTable; 
    
    @FXML JFXButton adminButton1;
    @FXML JFXButton userButton1;
    @FXML HBox adminButtons;
   
    TableColumn<beany, Integer> idCol = new TableColumn<>("id");
    TableColumn<beany, String> nameCol = new TableColumn<>("Document Name");
    TableColumn<beany, String> date1Col = new TableColumn<>("Date Added");
    TableColumn<beany, String> requestCol = new TableColumn<>("# of request");
    TableColumn<beany, HBox> actionCol = new TableColumn<>("Actions");
    @Override
    public void initialize(URL url, ResourceBundle rb) {
               idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nameDocument"));
        requestCol.setCellValueFactory(new PropertyValueFactory<>("requestno"));
        date1Col.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
        if(FXMLDocumentController.role.equals("admin")){
            adminButtons.getChildren().remove(0);
            
        }else{
            
           adminButtons.getChildren().remove(1);
        }
        
        documentTable.getColumns().addAll(nameCol,date1Col,requestCol);
        try {
            tableLoad("SELECT * FROM documents ORDER BY id DESC");
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(FXMLDocumentController.role.equals("admin")){
            documentTable.setRowFactory(e ->{
            
            TableRow<beany> row = new TableRow<>();
            row.setOnMouseClicked(z -> {
                
            if(z.getClickCount() == 2){
               try {
                  
                    further();
                    masterResource = z;
                    beany bens = row.getItem();
                    masterId = bens.id;
                    documentName = bens.nameDocument;
                    
                    
                } catch (IOException ex) {
                       System.out.println(ex.getMessage());
                }
            }
            });
            return row;
    });
        }
        
        
        
        
    }
    public void further()throws IOException{
        newStage("further.fxml");
    }
    @FXML public void search() throws SQLException{
        String val = searVal.getText().trim();
        tableLoad("SELECT * FROM documents WHERE name LIKE '%"+val+"' ORDER BY id DESC");
    }
    
      public void tableLoad(String q)throws SQLException{
        ObservableList<beany> apple = FXCollections.observableArrayList();
        
        
        Statement stm = stash.conn().createStatement();
        stm.executeQuery(q);
        ResultSet set = stm.getResultSet();
        while (set.next()) {            
            apple.add(new beany(set.getInt("id"), set.getString("name"), set.getString("dates"), counter(set.getInt("id"))));
        }
        documentTable.setItems(apple);
        
    }
      public int counter(int id) throws SQLException{
          
          String q = "SELECT * FROM requests WHERE document_id = "+id+" ORDER BY id DESC";
          Statement stm = stash.conn().createStatement();
          stm.executeQuery(q);
          ResultSet set = stm.getResultSet();
          int num = 0;
          while (set.next()){              
              num += 1;
          }
         return num;
      }
      
  public void addRequest(Event e)throws IOException{
      
      int selected = documentTable.getSelectionModel().getSelectedIndex();
       
        
     
      if(selected > -1){
           masterId = documentTable.getSelectionModel().getSelectedItem().getId();
           documentName = documentTable.getSelectionModel().getSelectedItem().nameDocument;
          Stage primary = new Stage();
        primary.initStyle(StageStyle.UNDECORATED);
         AnchorPane pane = FXMLLoader.load(getClass().getResource("addRequest.fxml"));
        Scene s = new Scene(pane);
        primary.setScene(s);
        primary.sizeToScene();
        primary.initModality(Modality.APPLICATION_MODAL);
        Stage toClose = (Stage) ((Node)e.getSource()).getScene().getWindow();
        
        primary.show();
      }else{
          new Alert(Alert.AlertType.INFORMATION,"Please select from the table above!").showAndWait();
          
      }
      
      
        
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


 @FXML public void addDocument(Event e)throws IOException{
         Stage primary = new Stage();
        primary.initStyle(StageStyle.UNDECORATED);
        AnchorPane pane = FXMLLoader.load(getClass().getResource("addPopup.fxml"));
        Scene s = new Scene(pane);
        primary.setScene(s);
        primary.sizeToScene();
        primary.initModality(Modality.APPLICATION_MODAL);
         
        Stage toClose = (Stage) ((Node)e.getSource()).getScene().getWindow();
        primary.show();
     }
 
 
 public class beany{
    private int id;
    private String nameDocument;
    private String dateAdded;
    private int requestno;

        public int getRequestno() {
            return requestno;
        }

        public void setRequestno(int requestno) {
            this.requestno = requestno;
        }

        public beany(int id, String nameDocument, String dateAdded,int requestno) {
            this.id = id;
            this.nameDocument = nameDocument;
            this.dateAdded = dateAdded;
            this.requestno = requestno;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNameDocument() {
            return nameDocument;
        }

        public void setNameDocument(String nameDocument) {
            this.nameDocument = nameDocument;
        }

        public String getDateAdded() {
            return dateAdded;
        }

        public void setDateAdded(String dateAdded) {
            this.dateAdded = dateAdded;
        }
    
    
    
    }
    
}
