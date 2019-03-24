package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import javax.swing.*;

import static com.sun.glass.ui.Cursor.setVisible;

public class LoginView extends Application {

    String user = "user";
    String pw = "password";
    String checkUser, checkPw;

    private JTextField usernameTextField;
    private JTextField nameTextField;
    private JTextField emailTextField;
    private JTextField cnpTextField;
    private JPasswordField passwordTextField;
    private JButton loginButton;
    private JButton registerButton;

    public static void main(String args[]){
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
