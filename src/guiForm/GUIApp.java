package guiForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class GUIApp {
    private String adminId = "123456789";
    public boolean isAdmin;
    private guiForm.DBWrapper wrapper;
    //enroll button
    protected JButton enrollButton;
    //main display
    protected JPanel mainPanel;
    private JComboBox ClassBox;
    private JTextField FNameText;
    private JTextField LNameText;
    private JTextField IdText;
    private JLabel classesLabel;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel studentIdLabel;
    //drop down class list
    protected JComboBox dropDownList;
    //connection
    private static Connection connect;



    public GUIApp()
    {
        wrapper = new guiForm.DBWrapper();

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
                    }
                    else
                    {
                        addClass();
                    }
                }
                else
                {
                    if (isAdmin) {
                        switchStudent();
                    }
                    else
                    {
                        enrollStudent();
                    }
                }
                JOptionPane.showMessageDialog(null, "You have been enrolled!");
            }
        });

        dropDownList.addItem("Classes");
    }

    public static void main(String[] args) throws SQLException
    {


        JFrame guiFrame = new JFrame("GUItoDB");
        guiFrame.setContentPane(new GUIApp().mainPanel);
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // always items to be selected from comboBox
        guiFrame.pack();
        guiFrame.setVisible(true);

    }

   private void switchStudent()
   {

   }

   private void switchAdmin()
   {

   }

   private void enrollStudent()
   {

   }

   private void addClass()
   {

   }

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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
