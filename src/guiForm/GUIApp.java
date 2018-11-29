package guiForm;

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class GUIApp {
    //enroll button
    protected JButton enrollButton;
    //main display
    protected JPanel mainPanel;
    //drop down class list
    protected JComboBox dropDownList;
    //connection
    private static Connection connect;



    public GUIApp()
    {
        enrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // TO DO: warning message that cap had been reached and s/he has been put on a wait list for the class.
                JOptionPane.showMessageDialog(null, "You have been enrolled!");
            }
        });

        dropDownList.addItem("Classes");
    }

    public static void main(String[] args) throws SQLException
    {
        Scanner readInput = new Scanner(System.in);
        System.out.println("Type 1 for admin and 2 for student: ");
        // read user input
        int input = readInput.nextInt();
        readInput.close();
        // if user is admin
        if (input == 1|| input == 2)
        {
            adminApp();
        }

        JFrame guiFrame = new JFrame("GUItoDB");
        guiFrame.setContentPane(new GUIApp().mainPanel);
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // always items to be selected from comboBox
        guiFrame.pack();
        guiFrame.setVisible(true);

    }

   public static void adminApp(){

    try{
        // get driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // establish connection
        connect = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/enrollment","root","chacha200");
        // create statement ready for sql
        Statement stat = connect.createStatement();
        stat.executeUpdate("INSERT INTO class (class_id, class_name, cap) VALUES ('2134', 'CS 345', '1')");
        stat.executeUpdate("INSERT INTO class (class_id, class_name, cap) VALUES ('6891', 'CS 400', '1')");
        connect.close();
    }catch(Exception e){ System.out.println(e);}
}
}
