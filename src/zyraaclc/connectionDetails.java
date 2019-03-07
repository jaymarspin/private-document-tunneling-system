
package zyraaclc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class connectionDetails {
    
    
    public Connection conn()throws SQLException{
       //Connection conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/parish6?useLegacyDatetimeCode=false&serverTimezone=UTC","root12root","astraynotjaymar");
        
       Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/parish?useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
        
        
        
        return conn;
    }
}
