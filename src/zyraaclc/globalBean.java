/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zyraaclc;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;


public class globalBean {
     //global beans
 private int id;
     //global beans
 
 //requestListController beans
 private String requestorName;
 private String rdate;
 private String ndate;
 private HBox actions;
//requestListController beans
 
 
 //usersColtroller beans
 private String fname;
 private String lname;
 private String username;
 private String password;
 private String email;
 //usersColtroller beans
 
 
 
 //myrequests beans
 private String docName;
 private String remarks;
 private StackPane actionBars;
//myrequests beans

    public StackPane getActionBars() {
        return actionBars;
    }

    public void setActionBars(StackPane actionBars) {
        this.actionBars = actionBars;
    }
 
 
 

 
 
    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

   public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
 
 
 
    
    
    
    

    public globalBean(int id, String fname, String lname, String username, String password,String email) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
 
 
 
 
 
    public globalBean(int id, String requestorName, String rdate, String ndate, HBox actions) {
        this.id = id;
        this.requestorName = requestorName;
        this.rdate = rdate;
        this.ndate = ndate;
        this.actions = actions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRequestorName() {
        return requestorName;
    }

    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getNdate() {
        return ndate;
    }

    public void setNdate(String ndate) {
        this.ndate = ndate;
    }

    public HBox getActions() {
        return actions;
    }

    public void setActions(HBox actions) {
        this.actions = actions;
    }
    
     public globalBean(int id,String docName,String rdate,String ndate,String remarks,StackPane actionBars){
     this.id = id;
     this.docName = docName;
     this.rdate = rdate;
     this.ndate = ndate;
     this.remarks = remarks;
     this.actionBars = actionBars;
 }

   
    
    
 
}
