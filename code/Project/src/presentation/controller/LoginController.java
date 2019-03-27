package presentation.controller;

import bll.course.CourseService;
import bll.student.StudentService;
import bll.user.AuthenticationService;
import dal.model.User;
import dal.model.validation.Notification;
import dal.repository.user.AuthenticationException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

import static utility.ProjectConstants.*;

public class LoginController {
    Stage window;
    private final AuthenticationService authenticationService;
    private final CourseService courseService;
    private final StudentService studentService;

    public TextField usernameField;
    private PasswordField passwordField;
    private Label registerLabel;
    private Button loginButton;
    private Button registerButton;
    private Button loveButton;


    public LoginController(AuthenticationService authenticationService, CourseService courseService, StudentService studentService) throws FileNotFoundException {

        window = new Stage();
        window.setTitle(LOGIN_TITLE);

        this.authenticationService = authenticationService;
        this.courseService=courseService;
        this.studentService=studentService;

        BorderPane layout = new BorderPane();
        layout.setId("root");
        VBox topPane = new VBox(40);
        topPane.setAlignment(Pos.CENTER);
        topPane.setPadding(new Insets(100,20,20,20));


        VBox leftPane = new VBox(40);
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setPadding(new Insets(20,20,20,400));
        Label label1 = new Label("Username");
        Label label2 = new Label("Password");
        leftPane.getChildren().addAll(label1,label2);

        VBox rightPane = new VBox(30);
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPadding(new Insets(20,400,20,20));
        usernameField = new TextField();
        usernameField.setMinWidth(300);
        passwordField = new PasswordField();
        passwordField.setMinWidth(300);

        loveButton=new Button();
        loveButton.setPadding(new Insets(10,10,10,10));
        Image image = new Image(getClass().getResourceAsStream("utcn.png"));
        loveButton.setGraphic(new ImageView(image));
        rightPane.getChildren().addAll(usernameField,passwordField);

        VBox bottomPane = new VBox( 30);
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setPadding(new Insets(20,20,100,20));
        loginButton = new Button("Login");
        loginButton.setOnAction(e->handleLoginButtonEvent());
        registerButton = new Button("Register");
        registerButton.setOnAction(e->handleRegisterButtonEvent());
        registerLabel=new Label("If you do not have an account, press register.");
        bottomPane.getChildren().addAll(loginButton,registerLabel,registerButton,loveButton);

        layout.setTop(topPane);
        layout.setLeft(leftPane);
        layout.setRight(rightPane);
        layout.setBottom(bottomPane);
        Scene scene = new Scene(layout,1200,800);
        window.setScene(scene);
        window.show();
    }

    private void handleLoginButtonEvent(){
        String username = usernameField.getText();
        String password = passwordField.getText();

        UserController userController;
        StudentController studentController;

        Notification<User> loginNotification = null;
        try {
            loginNotification = authenticationService.login(username, password);
        } catch (AuthenticationException e1) {
            e1.printStackTrace();
        }

        if (loginNotification != null) {
            if (loginNotification.hasErrors()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(LOGIN_FAIL);
                alert.setHeaderText(LOGIN_FAIL_MESSAGE);
                alert.setContentText(loginNotification.getFormattedErrors());
                alert.showAndWait();
            } else {
                if(USER_TYPE == 1)
                    studentController = new StudentController(authenticationService, courseService, studentService);
                else
                    userController = new UserController(authenticationService, courseService, studentService);
                window.close();
            }
        }

    }

    private void handleRegisterButtonEvent() {

        try {
            new RegisterController(authenticationService, courseService, studentService);
            window.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
