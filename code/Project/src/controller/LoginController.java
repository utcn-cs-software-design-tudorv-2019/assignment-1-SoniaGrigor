package controller;

import model.User;
import model.validation.Notification;
import repository.user.AuthenticationException;
import service.user.AuthenticationService;
import view.LoginView;
import view.UserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private final LoginView loginView;
    private final UserView userView;
    private final AuthenticationService authenticationService;

    public LoginController(LoginView loginView, UserView userView, AuthenticationService authenticationService) {
        this.loginView = loginView;
        this.userView=userView;
        this.authenticationService = authenticationService;
        loginView.setLoginButtonListener(new LoginButtonListener());
        loginView.setRegisterButtonListener(new RegisterButtonListener());
        userView.setLogoutButtonListener(new LogoutButtonListener());
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = null;
            try {
                loginNotification = authenticationService.login(username, password);
            } catch (AuthenticationException e1) {
                e1.printStackTrace();
            }

            if (loginNotification != null) {
                if (loginNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
                } else {
                    //JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");
                    userView.setVisible(true);
                    userView.getUserNameField().setText(username);
                }
            }
        }
    }

    private class RegisterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String name= loginView.getName();
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            String email= loginView.getEmail();
            String cnp= loginView.getCNP();
            Notification<Boolean> registerNotification = authenticationService.register(name, username, password, email, cnp);
            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration successful!");
                }
            }
        }
    }

    private class LogoutButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            userView.dispose();
        }
    }

}
