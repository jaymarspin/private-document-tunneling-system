
package zyraaclc;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class NotificationController implements Initializable {

    @FXML TableView<bean> notfi_table;
    TableColumn<bean, Integer> num;
    Connection conn;
    TableColumn<bean, String> info;
    TableColumn<bean, String> status;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        num = new TableColumn<>("Count");
        info = new TableColumn<>("Notification");
        status = new TableColumn<>("Status");
       
       
       
       
       
       
        num.setCellValueFactory(new PropertyValueFactory<>("num"));
        info.setCellValueFactory(new PropertyValueFactory<>("info"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        notfi_table.getColumns().addAll(num,info,status);
        try{
             conn = new connectionDetails().conn();
             notfi_table.setItems(tabledata("select * from requests where requestorId = "+FXMLDocumentController.id+" order by id desc"));
        }catch(SQLException z){
            new Alert(Alert.AlertType.ERROR,z.getMessage()).show();
        }
       
        
    }   
    
    public ObservableList<bean> tabledata(String q) throws SQLException{
        ObservableList<bean> apple = FXCollections.observableArrayList();
        
        Statement stm = conn.createStatement();
         stm.execute(q);
         ResultSet set = stm.getResultSet();
         int count = 0;
         String status = "";
        while (set.next()) {      
            count += 1;
            status = "Unread";
            if(set.getBoolean(8) != false){
                status = "Read";
                
                
            }
            
            Statement stm3 = conn.createStatement();
            stm3.executeQuery("select * from documents where id = "+set.getInt(2));
            ResultSet set2 = stm3.getResultSet();
            String docname = "";
            while (set2.next()) {                
                docname = set2.getString(2);
            }
            
            apple.add(new bean(count, "your request "+docname+" has been approved with requested date "+set.getString(5)+" and needed date "+set.getString(6),status));
            
            
            Statement stm2 = conn.createStatement();
            stm2.executeUpdate("update requests set notif = 1 where id = "+set.getInt(1));
            
        }
        return apple;
    }
    
    public class bean{
        private int num;
        private String info;
        private String status;

        public bean(int num, String info, String status) {
            this.num = num;
            this.info = info;
            this.status = status;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

       
        
    }
    
}
