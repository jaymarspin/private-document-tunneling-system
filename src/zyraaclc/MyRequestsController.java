/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zyraaclc;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;


public class MyRequestsController implements Initializable{

    
    
     String res = "";
   
    InputStream input = null;
    OutputStream output = null;
    ResultSet rs = null;
   
    
    private Connection con;
    @FXML TableView<globalBean> myRequestTable;
    TableColumn<globalBean,Integer> idCol = new TableColumn<>("id");
    TableColumn<globalBean,String> documentNameCol = new TableColumn<>("Document Name");
    TableColumn<globalBean,String> dateR = new TableColumn<>("Date Requested");
    TableColumn<globalBean,String> dateN = new TableColumn<>("Date Needed");
    TableColumn<globalBean,String> remarkCol = new TableColumn<>("Remark");
    TableColumn<globalBean,AnchorPane> actionCol = new TableColumn<>("Action");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = new connectionDetails().conn();
        } catch (SQLException ex) {
            Logger.getLogger(MyRequestsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        documentNameCol.setCellValueFactory(new PropertyValueFactory<>("docName"));
        dateR.setCellValueFactory(new PropertyValueFactory<>("rdate"));
        dateN.setCellValueFactory(new PropertyValueFactory<>("ndate"));
        remarkCol.setCellValueFactory(new PropertyValueFactory<>("remarks"));
        actionCol.setCellValueFactory(new PropertyValueFactory<>("actionBars"));
       
        
       myRequestTable.getColumns().addAll(documentNameCol,dateR,dateN,remarkCol,actionCol);
       
        try {
            myRequestTable.setItems(tableLoad());
        } catch (SQLException ex) {
            Logger.getLogger(MyRequestsController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }  
    
    public ObservableList<globalBean> tableLoad() throws SQLException{
        ObservableList<globalBean> stash = FXCollections.observableArrayList();
        
        Statement stm = con.createStatement();
        String q = "SELECT * FROM requests WHERE requestorId = "+FXMLDocumentController.id+" ORDER BY id DESC";
        stm.executeQuery(q);
        ResultSet set = stm.getResultSet();
        
        while (set.next()) {
            StackPane paner = new StackPane();
            JFXButton downloadBut = new JFXButton();
            downloadBut.setStyle("-fx-background-color: #ccc");
            downloadBut.setId(""+set.getInt("document_id"));
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOUD_DOWNLOAD);
            
            downloadBut.setGraphic(icon);
            paner.getChildren().add(downloadBut);
            String remarks;
        if(set.getInt("approved") == 1){
            remarks = "approved";
            
        }else{
            remarks = "waiting";
        }
            stash.add(new globalBean(set.getInt("id"), getDocName(set.getInt("document_id")), set.getString("rDate"), set.getString("nDate"), remarks, paner));
            downloadBut.setOnAction(e -> {
                Button b = (Button)e.getSource();
                String differ = Integer.parseInt(b.getId())+"-"+remarks;
                String splitter[] = differ.split("-");
                if(splitter[1].equals("approved")){
                    String home = System.getProperty("user.home");
                try{
                     String SQL = "SELECT * FROM documents WHERE id = "+splitter[0];
                     Statement stmm = con.createStatement();
                    stmm.executeQuery(SQL);
                    rs = stmm.getResultSet();
                   
                    System.out.println("Getting file please be patient..");
                               output = new FileOutputStream(new File(home+"/Downloads/parish-document-"+new Random(10000)+".docx"));        
                    while (rs.next()) {
                         
                              
                            	InputStream input = rs.getBinaryStream(3);
                                res = rs.getBinaryStream(4).toString();
                    
                    byte[] buffer = rs.getBytes(4);
                
                    output.write(buffer);
                
                
                output.flush();
                output.close();
                
                        
        }
                    
                }catch(Exception ee){
                    System.out.println(ee.getMessage()+"awdwd");
                }finally {
            try {
                if (rs != null) {
                    System.out.println(res);
                    new Alert(Alert.AlertType.INFORMATION,"Download Complete").show();
                    
                    rs.close();
                }
                    } catch (SQLException ee) {
                         System.out.println(ee.getMessage());
                    }         
                }
                }else{
                    new Alert(Alert.AlertType.ERROR,"you're not permitted to download the file yet").show();
                }
                
                
            });
        
        }
        
        
        
        
        
        return stash;
    }
    
    public String getDocName(int id) throws SQLException{
        Statement stm = con.createStatement();
        String q = "SELECT id,name FROM documents WHERE id = "+id;
        stm.executeQuery(q);
        ResultSet set = stm.getResultSet();
        while (set.next()) {            
            return set.getString("name");
        }
        
        return null;
    }
    
    
}
