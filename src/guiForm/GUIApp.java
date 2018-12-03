package guiForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class GUIApp {
    private String adminId = "123456789";
<<<<<<< HEAD
    private boolean isAdmin = false;
    private guiForm.Logic logic;
=======
    public boolean isAdmin;
    private boolean addableClass;
    // I am assuming we are using this wrapper for a session's DB connection -- Jess
    private guiForm.DBWrapper wrapper;
>>>>>>> master
    //enroll button
    protected JButton enrollButton;
    //main display
    protected JPanel mainPanel;
    private JComboBox ClassBox;
    private JTextField FNameText;
    private JTextField LNameText;
    private JTextField IdText;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JButton removeClassButton;
    //connection
    // private static Connection connect;



    public GUIApp()
    {
        logic = new guiForm.Logic();
        enrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String userId = IdText.getText();
                if(userId.equals(adminId))
                {
                    if(!isAdmin)
                    {
                        switchAdmin();
                        JOptionPane.showMessageDialog(null, "It seems you are an admin, Please" +
                                "Enter The Appropriate Info.");
                        isAdmin = true;
                    }
                    else
                    {
                        String className = FNameText.getText();
                        String capacity = LNameText.getText();
                        logic.addClass(className, capacity);
                    }
                }
                else
                {
                    if (isAdmin) {
                        switchStudent();
                        JOptionPane.showMessageDialog(null, "It seems that you are a student, " +
                                "Please enter the appropriate Info.");
                        isAdmin = false;
                    }
                    else
                    {
                        Object item = ClassBox.getSelectedItem();
                        String fName = FNameText.getText();
                        String lName = LNameText.getText();
                        logic.enrollStudent(item, fName, lName, userId);
                    }
                }

            }
        });
        removeClassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Object item = ClassBox.getSelectedItem();
                logic.removeClass(item);
            }
        });
    }

    public static void main(String[] args) throws SQLException
    {


        JFrame guiFrame = new JFrame("GUItoDB");
        guiFrame.setContentPane(new GUIApp().mainPanel);
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.pack();
        guiFrame.setVisible(true);
    }

   private void switchStudent()
   {
       firstNameLabel.setText("First Name");
       lastNameLabel.setText("Last Name");
       removeClassButton.setVisible(false);
       enrollButton.setText("Enroll");
   }

   private void switchAdmin()
   {
       firstNameLabel.setText("Classname");
       lastNameLabel.setText("Class Capacity");
       removeClassButton.setVisible(true);
       enrollButton.setText("Add Class");
   }

   private update

   public static void adminApp(){

    try{
        // get driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // establish connection
        connect = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/enrollment","root","enter password here");
        // create statement ready for sql
        Statement stat = connect.createStatement();
        stat.executeUpdate("INSERT INTO class (class_id, class_name, cap) VALUES ('2134', 'CS 345', '1')");
        stat.executeUpdate("INSERT INTO class (class_id, class_name, cap) VALUES ('6891', 'CS 400', '1')");
        connect.close();
    }catch(Exception e){ System.out.println(e);}
}
}
