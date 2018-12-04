package guiForm;

import com.mysql.cj.xdevapi.InsertStatement;

import javax.swing.plaf.nimbus.State;
import javax.swing.plaf.synth.SynthScrollBarUI;
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
    public String enroll(int studentIDinput, String student_fist, String student_last,int classIDinput) throws SQLException
    {
        Boolean studentExist;
        Boolean classExist;
        int classID;
        int capacity;
        Boolean studentAlreadyEnrolled;


        try {

            studentExist = studentExists(studentIDinput);
            if (studentExist == false)
            {
                // add student to the student DB
                addStudent(studentIDinput, student_fist,student_last);
            }
            // access DB to check cap
            // create statement
            Statement selectStat = connect.createStatement();
            //run query to search all classes
            ResultSet classDB = selectStat.executeQuery("SELECT * FROM class");

            // check if there are classes to enroll in
            boolean classAvailable = classDB.next();
            // if the class exists in the database

            // if there is a class in the DB
            if(classAvailable) {
                do {
                    // get class id
                    classID = classDB.getInt("class_id");
                    //get class capacity
                    capacity = classDB.getInt("cap");
                    // compare user input with class and see if able to enroll


                    if (classID == classIDinput) {

                        studentAlreadyEnrolled = studentPrevEnrolled(classIDinput, studentIDinput);
                        System.out.println(studentAlreadyEnrolled);

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
                        // add to wait_list

                        //
                        else {
                            // create state to prepare update
                            Statement wait_listStat = connect.createStatement();

                            // student id and class id to wait_list table
                            wait_listStat.executeUpdate("INSERT INTO wait_list (student_id, class_id, waitListQueue)"
                                    + " VALUES ('" + studentIDinput + "', '" + classID + "', '" + 1 + "')");

                            // return wait list message
                            return "You have been added to a wait list";

                        }

                    }
                }
                // iterate through DB to extract data
                while (classDB.next());
            }

            else
            {
                return "Class not available for enrollment.";
            }


        }
        catch (Exception excpt){System.out.println("Connection failed.  Error: " + excpt);}
        return "Enrolled successfully.";
    }

    // TODO: make separate method for:  if student exists

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

//    // check if class exists
//    boolean doesClassExists(int classIDinput) throws SQLException {
//        int classID;
//
//
//        // access DB to check cap
//        // create statement
//        Statement selectStat = connect.createStatement();
//        //run query to search all classes
//        ResultSet classDB = selectStat.executeQuery("SELECT * FROM class");
//
//        // check if there are classes to enroll in
//        boolean classAvailable = classDB.next();
//
//        // if there is a class in the DB
//        if (classAvailable) {
//            do {
//                // get class id
//                classID = classDB.getInt("class_id");
//                //get class capacity
//                // if class exists
//                if(classID == classIDinput)
//                {
//                    return true;
//                }
//
//            } while (classDB.next());
//        }
//        return false;
//    }


    // add new student to student DB
    public void addStudent(int student_id, String student_fname, String student_lname) throws SQLException
    {
        Statement studentStat = connect.createStatement();
        studentStat.executeUpdate("INSERT INTO student (student_id, student_first, student_last) "
                + "VALUES ('" + student_id + "', '" + student_fname + "', '" + student_lname + "')");


    }

    // check if student has enrolled before
    public Boolean studentPrevEnrolled(int classIDinput, int studentIDinput) throws SQLException {
        int studentID;
        int enrolledClass;
        int enrolledCap;

        Statement selectStat = connect.createStatement();

        // need to see if student has enrolled before
        ResultSet enrollDB = selectStat.executeQuery("SELECT * FROM enroll");

        //check if there is anything in DB
        boolean studentEnrolled = enrollDB.first();
        System.out.println(studentEnrolled);
        // check if student has already enrolled in class
        if (studentEnrolled) {
            do {
                // get student id
                studentID = enrollDB.getInt("student_id");
                // get classes students have enrolled in
                enrolledClass = enrollDB.getInt("class_id");
                if (classIDinput == enrolledClass && studentID == studentIDinput) {
                    return true;
                }
            } while (enrollDB.next());

        }
        return false;
    }
//    public String addClassAdmin(int classID) throws SQLException
//    {
//        Statement stat = connect.createStatement();
//        stat.executeUpdate("INSERT INTO class (class_id, class_name, cap) VALUES ('2134', 'CS 345', '0')");
//        stat.executeUpdate("INSERT INTO class (class_id, class_name, cap) VALUES ('6891', 'CS 400', '0')");
//        connect.close();
//    }

}
