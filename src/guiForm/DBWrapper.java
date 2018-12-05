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
                    "jdbc:mysql://localhost:3306/enrollment", "root", "GUItoDB");
        }
        catch (Exception excpt){System.out.println("Connection failed.  Error: " + excpt);}
    }

    // Student Queries //
    // uses connection to attempt enrollment process
    // enroll student
    public String enrollStudent(int studentIDinput, String student_first, String student_last, String classNameinput) throws SQLException
    {
        try {
            Boolean studentExist = studentExists(studentIDinput);
            Boolean classExist = doesClassExists(classNameinput);
            int classID;
            String className;
            int capacity;
            Boolean studentAlreadyEnrolled;

            // if student does not exist, add the student
            if (!studentExist) {
                // add student to student DB
                addStudent(studentIDinput, student_first, student_last);
            }

            // prepare to iterate through database
            Statement selectStat = connect.createStatement();

            //run query to search all classes
            ResultSet classDB = selectStat.executeQuery("SELECT * FROM class");

            // check if there are classes to enroll in
            boolean classAvailable = classDB.next();

            // if the class exists in the database
            if (!classExist)
            {
                return "Class does not exist.";
            }
            // if there is a class in the DB
//
            if (classAvailable) {
                do {
                    // get class id
                    classID = classDB.getInt("class_id");
                    // get class name
                    className = classDB.getString("class_name");
                    //get class capacity
                    capacity = classDB.getInt("cap");
                    // compare user input with class and see if able to enroll

                    // if the class is able to be enrolled in
                    if (classNameinput.equals(className)) {

                        studentAlreadyEnrolled = studentPrevEnrolled(classID, studentIDinput);
                        // if the cap is 0 and student is not already enrolled
                        if (capacity == 0 && !studentAlreadyEnrolled) {
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
                        // check if already enrolled, add to wait_list
                        else {

                            // check if enrolled
                            if (studentAlreadyEnrolled)
                            {
                                return "You have already been enrolled in this class.";
                            }
                            // check waitlist
                                return waitList(studentIDinput, classID);
                        }

                    }
                }
                // iterate through DB to extract data
                while (classDB.next());
            }
        }catch (Exception e){System.out.println("Connection failed.  Error: " + e);}

        return "You have been enrolled.";
    }

    // wait list method
    public String waitList(int student_id, int class_id) throws SQLException
    {
        int studID;
        int classID;
        int queue = 1;

        Statement wait_listStat = connect.createStatement();

        //check for if other student has waitlisted the class

        ResultSet wait_listDB = wait_listStat.executeQuery("SELECT * FROM wait_list");

        boolean waitListHasStudent = wait_listDB.next();

        if(waitListHasStudent)
        {
            do {
                // get values in wait list
                studID = wait_listDB.getInt("student_id");
                classID = wait_listDB.getInt("class_id");
                queue = wait_listDB.getInt("waitListQueue");

                if (student_id == studID)
                {

                    // return message with queue number
                    return "You are already on the waitlist.  Position: " + queue;
                }

                // someone is already on the wait list for that class
                else if (class_id == classID)
                {
                    // add 1 to their queue number
                    queue = queue + 1;

                }

            } while(wait_listDB.next());
        }
        // add student to wait list
        wait_listStat.executeUpdate("INSERT INTO wait_list (student_id, class_id, waitListQueue)"
                + " VALUES ('" + student_id + "', '" + class_id + "', '" + queue + "')");
        return "You have been added to the wait list.";
    }

    // if student exists
    public Boolean studentExists(int studentIDinput) throws SQLException
    {
        int studentID;

        Statement selectStat = connect.createStatement();
        //run query to search all students
        ResultSet studentDB = selectStat.executeQuery("SELECT * FROM student");

        // check if there are students in table
        boolean studentAvailable = studentDB.first();

        // if there is a class in the DB
        if (studentAvailable) {
            do {
                // get student id
                studentID = studentDB.getInt("student_id");
                if (studentID == studentIDinput)
                {
                    return true;
                }
            } while (studentDB.next());
        }

        // otherwise, student does not exist
        return false;

}

    // check if class exists
    boolean doesClassExists(String classNameinput) throws SQLException {
        String className;


        // access DB to check cap
        // create statement
        Statement selectStat = connect.createStatement();
        //run query to search all classes
        ResultSet classDB = selectStat.executeQuery("SELECT * FROM class");

        // check if there are classes to enroll in
        boolean classAvailable = classDB.next();

        // if there is a class in the DB
        if (classAvailable) {
            do {
                // get class id
                className = classDB.getString("class_name");
                //get class capacity
                // if class exists
                if(className.equals(classNameinput))
                {
                    return true;
                }

            } while (classDB.next());
        }
        return false;
    }


    // add new student to student DB
    public void addStudent(int student_id, String student_fname, String student_lname) throws SQLException
    {
        Statement studentStat = connect.createStatement();
        studentStat.executeUpdate("INSERT INTO student (student_id, student_first, student_last) "
                + "VALUES ('" + student_id + "', '" + student_fname + "', '" + student_lname + "')");


    }

    // check if student has enrolled before
    public Boolean studentPrevEnrolled(int classID, int studentIDinput) throws SQLException {
        int studentID;
        int enrolledClass;
        int enrolledCap;

        Statement selectStat = connect.createStatement();

        // need to see if student has enrolled before
        ResultSet enrollDB = selectStat.executeQuery("SELECT * FROM enroll");
        //check if there is anything in DB
        boolean studentEnrolled = enrollDB.first();
        // check if student has already enrolled in class
        if (studentEnrolled) {
            do {
                // get student id
                studentID = enrollDB.getInt("student_id");
                // get classes students have enrolled in
                enrolledClass = enrollDB.getInt("class_id");
                if (enrolledClass == classID && studentID == studentIDinput) {
                    return true;
                }
            } while (enrollDB.next());

        }
        return false;
    }

    // add class thru admin
    public String addClassAdmin(int classID, String className) throws SQLException
    {
        int classIDdb;
        Statement classCheck = connect.createStatement();
        ResultSet classDB = classCheck.executeQuery("SELECT * FROM class");
        Boolean classesAvail = classDB.next();
        if (classesAvail)
        {
            do {
                classIDdb = classDB.getInt("class_id");

                if (classIDdb == classID)
                {
                    return "This class already exists.  To create a new section, use a different id.";
                }
            } while(classDB.next());
        }
        Statement stat = connect.createStatement();
        stat.executeUpdate("INSERT INTO class (class_id, class_name, cap) VALUES ('" + classID+ "', '" + className + "', '0')");
        String returnStatement = "Class " + className + " has been added.";
        return returnStatement;
    }

    // delete class thru admin

    public String deleteClassAdmin(String className) throws SQLException
    {
        Boolean classExist = doesClassExists(className);
        String returnStatement;
        // if the class exists in the db
        if(classExist)
        {

            Statement deleteClass = connect.createStatement();
            deleteClass.executeUpdate("DELETE FROM class WHERE class_name ='"+ className + "';");
            // drop
            returnStatement = "Class deleted.";
            return returnStatement;
        }
        returnStatement = "Class does not exist";
        return returnStatement;

    }
}
