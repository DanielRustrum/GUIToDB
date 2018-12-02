package guiForm;

import java.sql.*;
import java.sql.DriverManager;


public class DBWrapper {

    private static Connection connect;

    public DBWrapper()
    {
        try {
            // get driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // establish connection
            connect = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/enrollment", "root", "enter password here");
        }
        catch (Exception excpt){System.out.println("Connection failed.  Error: " + excpt);}
    }

    // Student Queries //

    // Admin Queries //
}
