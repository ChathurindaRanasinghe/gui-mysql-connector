import java.lang.*;
import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnect {
    public static Connection connect(){
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dept_db?","root","");
            System.out.println("Connection Successful!");
        } catch (Exception e){
            System.out.println(e);
        }
        return con;
    }
}
