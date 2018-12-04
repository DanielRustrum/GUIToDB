package guiForm;

import javax.swing.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class GUIApp {
    private String adminId = "123456789";

    private boolean isAdmin = false;
    private guiForm.Logic logic;

    // boolean isAdmin;
    private boolean addableClass;
    // I am assuming we are using this wrapper for a session's DB connection -- Jess
    private guiForm.DBWrapper wrapper;

    //enroll button
    protected JButton enrollButton;
    protected JPanel mainPanel;
    private JComboBox ClassBox;
    private JTextField FNameText;
    private JTextField LNameText;
    private JTextField IdText;
    private JLabel studentIdLabel;
    private JLabel classesLabel;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JButton removeClassButton;
    //connection
    // private static Connection connect;



    public GUIApp()
    {
        logic = new guiForm.Logic();
        updateComboBox();
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
                        updateComboBox();
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
       studentIdLabel.setText("Student ID");
       firstNameLabel.setText("First Name");
       lastNameLabel.setText("Last Name");
       classesLabel.setText("Class: ");
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


   private void enrollStudent()
   {
        // wrapper.enroll(student_id, student_first, student_last, class_id); // <-- need to pass through class that student selects
   }

   private void addClass()
   {
    // wrapper.addClassAdmin(int classID)
   }

   private void removeClass()
   {

   }


   private void updateComboBox()
   {
        ArrayList<String> classList = logic.getClasses();
        ClassBox.removeAllItems();
        for(int index = 0; index<classList.size(); index++)
       {
            ClassBox.addItem(classList.get(index));
       }
   }

}
