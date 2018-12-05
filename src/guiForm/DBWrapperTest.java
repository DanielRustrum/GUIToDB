package guiForm;

import java.sql.*;

public class DBWrapperTest {

    public static DBWrapper dbwrapp = new DBWrapper();

    public static void main(String[] args) throws SQLException
    {
        // attempt to enroll
        System.out.println(dbwrapp.enrollStudent(123, "Test", "Johnny", "idk"));

        // class not in table
        System.out.println(dbwrapp.enrollStudent(666, "new", "boi", "idk"));

        // new queue position
        System.out.println(dbwrapp.enrollStudent(777, "new", "boi", "idk"));

        // admin add
         System.out.println(dbwrapp.addClassAdmin("next"));


    }
}
