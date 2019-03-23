package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField usernameTextField;
    private JTextField nameTextField;
    private JTextField emailTextField;
    private JTextField cnpTextField;
    private JPasswordField passwordTextField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginView() {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(nameTextField);
        add(emailTextField);
        add(usernameTextField);
        add(passwordTextField);
        add(cnpTextField);
        add(loginButton);
        add(registerButton);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields() {
        nameTextField = new JTextField();
        emailTextField = new JTextField();
        usernameTextField = new JTextField();
        passwordTextField = new JPasswordField();
        cnpTextField = new JTextField();

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
    }
    public String getName() {
        return nameTextField.getText();
    }

    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getEmail() {
        return emailTextField.getText();
    }

    public String getCNP() {
        return cnpTextField.getText();
    }

    public String getPassword() {
        return new String(passwordTextField.getPassword());
    }

    public void setLoginButtonListener(ActionListener loginButtonListener) {
        loginButton.addActionListener(loginButtonListener);
    }

    public void setRegisterButtonListener(ActionListener registerButtonListener) {
        registerButton.addActionListener(registerButtonListener);
    }

}
