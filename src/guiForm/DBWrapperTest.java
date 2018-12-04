package guiForm;

import java.sql.*;

public class DBWrapperTest {

    public static DBWrapper dbwrapp = new DBWrapper();

    public static void main(String[] args) throws SQLException
    {
        dbwrapp.enroll(666, "new", "boi", 3);
    }
}
