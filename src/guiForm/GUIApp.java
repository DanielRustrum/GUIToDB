package guiForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIApp extends JPanel {
    private String adminId = "123456789";
    private boolean isAdmin = false;
    private Logic logic;

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
    private JLabel statusMessage;

    public GUIApp()
    {
        initComponents();
        logic = new Logic();
        removeClassButton.setVisible(false);
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
                        JOptionPane.showMessageDialog(null, "It seems you are an admin, Please " +
                                "Enter The Appropriate Info.");
                        isAdmin = true;
                    }
                    else
                    {
                        String className = FNameText.getText();
                        String capacity = LNameText.getText();
                        String result = logic.addClass(className, capacity);
                        statusMessage.setText(result);
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
                        String result = logic.enrollStudent((String)item, fName, lName, userId);
                        statusMessage.setText(result);
                    }
                }

            }
        });

        removeClassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Object item = ClassBox.getSelectedItem();
                String result = logic.removeClass((String)item);
                statusMessage.setText(result);
                updateComboBox();
            }
        });
    }

    public static void main(String[] args)
    {
        JFrame guiFrame = new JFrame("GUItoDB");
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUIApp newgui = new GUIApp();
        guiFrame.setContentPane(newgui.mainPanel);
        guiFrame.pack();
        guiFrame.setVisible(true);
    }

   private void switchStudent()
   {
       studentIdLabel.setText("Student ID");
       firstNameLabel.setText("First Name");
       lastNameLabel.setText("Last Name");
       classesLabel.setText("Classes");
       removeClassButton.setVisible(false);
       enrollButton.setText("Enroll");
   }

   private void switchAdmin()
   {
       firstNameLabel.setText("Classname");
       classesLabel.setText("Classes to remove");
       lastNameLabel.setText("Class Capacity");
       removeClassButton.setVisible(true);
       enrollButton.setText("Add Class");
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

    private void initComponents() {

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
