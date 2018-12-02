package guiForm;

import com.mysql.cj.xdevapi.InsertStatement;

import javax.swing.plaf.nimbus.State;
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
                    "jdbc:mysql://localhost:3306/enrollment", "root", "GUItoDB");
        }
        catch (Exception excpt){System.out.println("Connection failed.  Error: " + excpt);}
    }

    // Student Queries //
    // TODO: use connection to access classes, attempt to enroll, put in waitlist or enroll
    public String enroll(int studentIDinput,int classIDinput)
    {
        int classID;
        String className;
        int capacity;
        int queueNum;

        try {
            // access DB to check cap
            // create statement
            Statement selectStat = connect.createStatement();
            //run query to search all classes
            ResultSet classDB = selectStat.executeQuery("SELECT * FROM class");
            System.out.println(classDB);
            // check if there are classes to enroll in
            boolean classAvailable = classDB.next();
            System.out.println(classAvailable);
            // if there is a class in the DB
            if(classAvailable) {
                do
                    {
                    // get class id
                    classID = classDB.getInt("class_id");
                    // get class name
                    className = classDB.getString("class_name");
                    //get class capacity
                    capacity = classDB.getInt("cap");
                    // compare user input with class and see if able to enroll
                    if (classID == classIDinput)
                    {
                        // if they are able to enrol
                        if (capacity == 0)
                            {
                            // add one for freshly enrolled student
                            capacity += 1;

                            // create statement to prepare update
                            Statement enrollStat = connect.createStatement();

                            // add class id and student id to enroll table
                            enrollStat.executeUpdate("INSERT INTO enroll (student_id, class_id) "
                                    + "VALUES ('" + studentIDinput + "', '" + classID + "')");

                            // create new statement to update cap in table
                            Statement updateCap = connect.createStatement();

                            // update capacity in class table
                            updateCap.executeUpdate("UPDATE class SET cap = "
                                    + capacity
                                    + "  WHERE class_id = "
                                    + classID);
                            }
                        // add to wait_list
                        // TODO: get wait list working
//                        else
//                            {
//                                // create state to prepare update
//                                Statement wait_listStat = connect.createStatement();
//
//                                // student id and class id to wait_list table
//                                wait_listStat.executeUpdate("INSERT INTO wait_list (class_idWait, student_idWait)"
//                                + " VALUES ('" + studentIDinput + "', '" + classID + "')");
//
//                                // return wait list message
//                                return "You have been added to a wait list";
//
//                            }
//
                    }
                }
                // iterate through DB to extract data
                while (classDB.next());

            }
            else
            {
                return "No classes available to enroll in.";
            }


        }
        catch (Exception excpt){System.out.println("Connection failed.  Error: " + excpt);}
        return "Enrolled successfully.";
    }

    // Admin Queries //
}
