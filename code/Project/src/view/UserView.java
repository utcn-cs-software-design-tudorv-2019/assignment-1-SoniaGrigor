package view;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.sun.glass.ui.Cursor.setVisible;

public class UserView extends JFrame {
    private JTextField welcomeUserField;
    private JTextField userNameField;
    private JButton logoutButton;

    public UserView() {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(welcomeUserField);
        add(userNameField);
        add(logoutButton);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(false);
    }

    private void initializeFields() {
        welcomeUserField = new JTextField("Welcome to our store!");
        userNameField = new JTextField();
        logoutButton = new JButton("Logout");
    }

    public JTextField getWelcomeUserField() {
        return welcomeUserField;
    }

    public void setWelcomeUserField(JTextField welcomeUserField) {
        this.welcomeUserField = welcomeUserField;
    }

    public JTextField getUserNameField() {
        return userNameField;
    }

    public void setUserNameField(JTextField userNameField) {
        this.userNameField = userNameField;
    }

    public void setLogoutButtonListener(ActionListener logoutButtonListener) {
        logoutButton.addActionListener(logoutButtonListener);
    }

}
