
package zyraaclc;

import com.jfoenix.controls.JFXComboBox;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;


public class ReportController implements Initializable {

    
   String months[] = {"January","Febuary","March","April","May","June","July","August","September","October","November","December"};
   
   String typePopulation[] = {"Requests"};
    @FXML JFXComboBox<String> type;
    @FXML JFXComboBox<String> year;
    @FXML JFXComboBox<String> month;
    private Connection conn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try{
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        int base = 2017;
        
        int yearVal = Integer.parseInt(sdf.format(new Date()).split("-")[2]);
        int regex = yearVal - base;
        regex += 1;
        String years[] = new String[regex];
            
        int num = 0;
        while (num < regex) {  
            
            years[num] = String.format("%d", (int)(base));
            base += 1;
            num += 1;
            
        }
        year.getItems().addAll(years);
        month.getItems().addAll(months);
        type.getItems().addAll(typePopulation);
        type.getSelectionModel().select(0);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        year.getSelectionModel().select(0);
        month.getSelectionModel().select(0);
        
    }   
    
    @FXML public void done(){
         try {
             int count = 0;
             List<Map<String, Object>> dataSource = new ArrayList<>();
               Map<String,Object> row = new HashMap<>();
               Map<String,Object> row2 = new HashMap<>();
               
               String dateReport = month.getSelectionModel().getSelectedItem()+" of "+year.getSelectionModel().getSelectedItem();
               row2.put("datereport", dateReport);
               dataSource.add(row2);
               
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
             String dater = year.getSelectionModel().getSelectedItem()+"-"+(month.getSelectionModel().getSelectedIndex()+ 1)+"-1";
             String daterLimit = year.getSelectionModel().getSelectedItem()+"-"+(month.getSelectionModel().getSelectedIndex()+ 2)+"-1";
             if(month.getSelectionModel().getSelectedIndex() == 11){
                    daterLimit = (year.getSelectionModel().getSelectedItem()+1)+"1-1";
             }
             Date d = null;
             long val = 0;
             try{
                 
                 
                 d = sdf.parse(dater);
             }catch(Exception e){
                 
             }
             
             
             
             
             val = d.getTime();
             
             try{
                   

                 conn = new connectionDetails().conn();
                 String q = "select * from requests";
                 Statement stm = conn.createStatement();
                 stm.execute(q);
                 ResultSet set = stm.getResultSet();
                 String sb = null;
                 while (set.next()) {   
                     Date d1 = null;
                     Date limit = null;
                   try{
                       d1 = sdf.parse(set.getString(5));
                       limit = sdf.parse(daterLimit);
                       if(d1.getTime() >= val){
                           if(d1.getTime() < limit.getTime()){
                               count += 1;
                               int id = set.getInt(3);
                               int docid = set.getInt(2);
                               String q2 = "select * from users where id = "+id;
                               Statement stm2 = conn.createStatement();
                               stm2.executeQuery(q2);
                               ResultSet set2 = stm2.getResultSet();
                               System.out.println(""+docid);
                               String name = "";
                               while (set2.next()) {                                   
                                   name = set2.getString(2)+" "+set2.getString(3);
                                   
                               }
                               
                               String q3 = "select * from documents where id = "+docid;
                               Statement stm3 = conn.createStatement();
                               stm3.executeQuery(q3);
                               ResultSet set3 = stm3.getResultSet();
                               
                               String docname = "";
                               while (set3.next()) {                                   
                                   docname = set3.getString(2);
                                   
                               }
                               if(count == 0){
                                   name = "no result";
                               }
                               
                               row.put("requestor", name);
                               row.put("file", docname);
                               row.put("rdate", set.getString(5));
                            
                               dataSource.add(row);
                               
                             
                           }
                       }
                   }catch(Exception z){
                       
                   }
                 }
                 
//              SELECT column_name(s)
//FROM table1
//INNER JOIN table2
//ON table1.column_name = table2.column_name;  
                 
             }catch(SQLException e){
                 
             }
             if(count == 0){
                     row.put("requestor", "no result");
                               row.put("file", "-------");
                               row.put("rdate", "-------");          
                               }
                               
                               
             
             InputStream file = getClass().getResourceAsStream("/reports/newReport.jrxml");
  
//                    JasperReport jrReport = JasperCompileManager.compileReport(file);
//                    JasperPrint jrPrint = JasperFillManager.fillReport(jrReport, null,jrSource);
//                    JasperViewer jrView = new JasperViewer(jrPrint,false);
//                    jrView.setVisible(true);
                   JRDataSource jrSource = new JRBeanCollectionDataSource(dataSource);
                    JasperReport jrReport = JasperCompileManager.compileReport(file);
                    JasperPrint jrPrint = JasperFillManager.fillReport(jrReport, null,jrSource);
                    JasperViewer jrView = new JasperViewer(jrPrint,false);
                    jrView.setVisible(true);
                
            } catch (JRException z) {
                new Alert(Alert.AlertType.ERROR,z.getMessage()).showAndWait();
            } 
    }
    
    
    @FXML public void close(Event event){
        Stage window = (Stage) ((Node)event.getSource()).getParent().getScene().getWindow();
        window.close();
    }
        
    
}
