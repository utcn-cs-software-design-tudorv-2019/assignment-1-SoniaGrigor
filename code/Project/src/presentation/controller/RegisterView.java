package presentation.controller;

import bll.course.CourseService;
import bll.student.StudentService;
import bll.user.AuthenticationService;
import bll.user.UserService;
import dal.model.Course;
import dal.model.validation.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static dal.database.Constants.Roles.ROLES;
import static utility.ProjectConstants.*;

public class RegisterView {

    Stage window;
    private final AuthenticationService authenticationService;
    private final CourseService courseService;
    private final StudentService studentService;
    private final UserService userService;

    private TextField nameField;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField emailField;
    private TextField cnpField;
    private ChoiceBox roleListView;
    private Button registerButton;
    private List<Course> courseList;
    private CheckComboBox<String> courseListView;

    public RegisterView(AuthenticationService authenticationService, CourseService courseService, StudentService studentService, UserService userService) throws FileNotFoundException {

        window = new Stage();
        window.setTitle(REGISTER_TITLE);

        this.authenticationService= authenticationService;
        this.courseService=courseService;
        this.studentService= studentService;
        this.userService=userService;

        BorderPane layout = new BorderPane();
        layout.setId("root");
        VBox topPane = new VBox(40);
        topPane.setAlignment(Pos.CENTER);
        topPane.setPadding(new Insets(100,20,20,20));

        VBox leftPane = new VBox(40);
        leftPane.setAlignment(Pos.CENTER_RIGHT);
        leftPane.setPadding(new Insets(20,20,20,400));
        Label labelName = new Label("Name");
        Label labelUsername = new Label("Username");
        Label labelPassword = new Label("Password");
        Label labelEmail = new Label("Email");
        Label labelCNP = new Label("CNP");
        Label labelCourse = new Label("Courses");
        Label labelRole = new Label("Roles");
        //leftPane.getChildren().addAll(labelName,labelUsername, labelPassword, labelEmail, labelCNP, labelRole, labelCourse);
        leftPane.getChildren().addAll(labelName,labelUsername, labelPassword, labelEmail, labelCNP, labelRole);


        VBox rightPane = new VBox(30);
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPadding(new Insets(20,400,20,20));
        nameField= new TextField();
        nameField.setMinWidth(300);
        usernameField = new TextField();
        usernameField.setMinWidth(300);
        passwordField = new PasswordField();
        passwordField.setMinWidth(300);
        emailField = new TextField();
        emailField.setMinWidth(300);
        cnpField = new TextField();
        cnpField.setMinWidth(300);

        roleListView = new ChoiceBox ();
        roleListView.setMinWidth(300);
        ObservableList<String> roleItems = FXCollections.observableArrayList(ROLES);
        roleListView.getItems().setAll(
                roleItems.stream()
                        .map(String::toUpperCase)
                        .collect(Collectors.toList()));


        courseList= new ArrayList<Course>();
        courseList=courseService.findAll();
        courseListView =  new CheckComboBox<String>();
        courseListView.setMinWidth(300);
        ObservableList<String> courseItems = FXCollections.observableArrayList(
                courseList.stream()
                        .map(Course::getName)
                        .map(String::toUpperCase)
                        .collect(Collectors.toList()));
        courseListView.getItems().setAll(courseItems);

        //rightPane.getChildren().addAll(nameField, usernameField, passwordField, emailField, cnpField, roleListView, courseListView);
        rightPane.getChildren().addAll(nameField, usernameField, passwordField, emailField, cnpField, roleListView);

        VBox bottomPane = new VBox( 30);
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setPadding(new Insets(20,20,100,20));
        registerButton = new Button("Register");
        registerButton.setOnAction(e->handleRegisterButtonEvent());
        bottomPane.getChildren().addAll(registerButton);

        layout.setTop(topPane);
        layout.setLeft(leftPane);
        layout.setRight(rightPane);
        layout.setBottom(bottomPane);
        Scene scene = new Scene(layout,1200,800);
        window.setScene(scene);
        window.show();
    }

    private void handleRegisterButtonEvent() {

        String name = nameField.getText();
        String username= usernameField.getText();
        String password= passwordField.getText();
        String email=emailField.getText();
        String cnp = cnpField.getText();
        String role= roleListView.getSelectionModel().getSelectedItem().toString();
        Notification<Boolean> registerNotification = authenticationService.register(name, username, password, email, cnp );

        //Notification<Boolean> registerNotification = authenticationService.register(name, username, password, email, cnp, role, getSelectedCourses() );
        //ArrayList<String> selectedCourses = getSelectedCourses();
        //ArrayList<Integer> idCourses = courseService.getIdByName(selectedCourses);
        //int idUser = authenticationService.getLastIndex();
        //studentService.enrollCourses(idUser, idCourses);

        if (registerNotification != null) {
            if (registerNotification.hasErrors()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(REGISTER_FAIL);
                alert.setHeaderText(REGISTER_FAIL_MESSAGE);
                alert.setContentText(registerNotification.getFormattedErrors());
                alert.showAndWait();

            }else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(REGISTER_SUCCES);
                alert.setHeaderText(REGISTER_SUCCES);
                alert.setContentText(registerNotification.getFormattedErrors());
                alert.showAndWait();
                window.close();
                try {
                    new LoginView(authenticationService,courseService,studentService,userService ).usernameField.setText(username);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    private ArrayList<String> getSelectedCourses() {
        return new ArrayList<String>(courseListView.getCheckModel().getCheckedItems());
    }
}
