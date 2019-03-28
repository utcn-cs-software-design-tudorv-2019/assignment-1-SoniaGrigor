package presentation.controller;

import bll.course.CourseService;
import bll.student.StudentService;
import bll.user.AuthenticationService;
import bll.user.UserService;
import dal.model.Course;
import dal.model.Enrollment;
import dal.model.Student;
import dal.model.builder.StudentBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utility.Utility;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static utility.ProjectConstants.*;

public class StudentController {

    Stage window;
    Scene sceneMain;
    private AuthenticationService authenticationService;
    private StudentService studentService;
    private CourseService courseService;
    private UserService userService;

    private static Student student;
    int idUser = Utility.getLoggedUser();

    private TextField nameField;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField emailField;
    private TextField cnpField;
    private TextField cardNoField;
    private TextField groupField;
    private ArrayList<Course> courseList;
    private ChoiceBox courseListView;

    private Button updateButton;
    private Button deleteButton;
    private Button enrollButton;
    private Button viewCoursesButton;
    private Button back;


    public StudentController(AuthenticationService authenticationService, CourseService courseService, StudentService studentService, UserService userService) {
        window = new Stage();
        window.setTitle(STUDENT_TITLE);

        this.authenticationService = authenticationService;
        this.courseService = courseService;
        this.studentService = studentService;
        this.userService = userService;

        student = studentService.findById(idUser);

        BorderPane layout = new BorderPane();
        layout.setId("root");
        VBox topPane = new VBox(40);
        topPane.setAlignment(Pos.CENTER);
        topPane.setPadding(new Insets(100, 20, 20, 20));


        VBox leftPane = new VBox(40);
        leftPane.setAlignment(Pos.CENTER_RIGHT);
        leftPane.setPadding(new Insets(20, 20, 20, 400));
        Label labelName = new Label("Name");
        Label labelUsername = new Label("Username");
        Label labelPassword = new Label("Password");
        Label labelEmail = new Label("Email");
        Label labelCNP = new Label("CNP");
        Label labelCourse = new Label("Courses");
        Label labelCardNo = new Label("Card Number");
        Label labelGroup = new Label("Group");
        //leftPane.getChildren().addAll(labelName, labelUsername, labelPassword, labelEmail, labelCNP, labelCourse, labelCardNo, labelGroup);
        leftPane.getChildren().addAll(labelName, labelUsername, labelPassword, labelEmail, labelCNP, labelCardNo, labelGroup);

        VBox rightPane = new VBox(30);
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPadding(new Insets(20, 400, 20, 20));
        nameField = new TextField(student.getName());
        nameField.setMinWidth(300);
        usernameField = new TextField(student.getUsername());
        usernameField.setMinWidth(300);
        passwordField = new PasswordField();
        passwordField.setMinWidth(300);
        emailField = new TextField(student.getEmail());
        emailField.setMinWidth(300);
        cnpField = new TextField(student.getCNP());
        cnpField.setMinWidth(300);
        cardNoField = new TextField(Integer.toString(student.getCardNo()));
        cardNoField.setMinWidth(300);
        groupField = new TextField(student.getGroup());
        groupField.setMinWidth(300);

        courseListView = new ChoiceBox();
        courseListView.setMinWidth(300);
        courseList = new ArrayList<>(courseService.findAll());
        List<String> c = courseList.stream().map(Course::getName).collect(Collectors.toList());
        courseListView.getItems().setAll(c);
        //rightPane.getChildren().addAll(nameField, usernameField, passwordField, emailField, cnpField, courseListView, cardNoField, groupField);
        rightPane.getChildren().addAll(nameField, usernameField, passwordField, emailField, cnpField, cardNoField, groupField);

        VBox bottomPane = new VBox(30);
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setPadding(new Insets(20, 20, 100, 20));
        updateButton = new Button("Update Information");
        updateButton.setOnAction(e -> handleUpdateButtonEvent());
        deleteButton = new Button("Delete Accout");
        deleteButton.setOnAction(e -> handleDeleteButtonEvent());
        enrollButton = new Button("Enroll Course");
        enrollButton.setOnAction(e -> handleEnrollButtonEnvent(e));
        viewCoursesButton = new Button("View Courses");
        viewCoursesButton.setOnAction(e -> handleViewCoursesButtonEvent());
        bottomPane.getChildren().addAll(updateButton, deleteButton, enrollButton, viewCoursesButton);

        layout.setTop(topPane);
        layout.setLeft(leftPane);
        layout.setRight(rightPane);
        layout.setBottom(bottomPane);

        sceneMain = new Scene(layout, 1200, 800);
        window.setScene(sceneMain);
        window.show();
    }

    private void handleViewCoursesButtonEvent() {
        try {
            List<Enrollment> myCourseList = courseService.getMyCourses(idUser);
            ObservableList<Enrollment> data = FXCollections.observableList(myCourseList);

            TableView<Enrollment> table = new TableView<>();

            TableColumn nameCol = new TableColumn("Name");
            nameCol.setMinWidth(100);
            nameCol.setCellValueFactory(new PropertyValueFactory<Enrollment, String>("courseName"));

            TableColumn creditCol = new TableColumn("Credit");
            creditCol.setMinWidth(100);
            creditCol.setCellValueFactory(new PropertyValueFactory<Enrollment, Integer>("creditCourse"));

            TableColumn examCol = new TableColumn("Exam Date");
            examCol.setMinWidth(200);
            examCol.setCellValueFactory(new PropertyValueFactory<Enrollment, Date>("dateExam"));

            TableColumn roomCol = new TableColumn("Room");
            roomCol.setMinWidth(200);
            roomCol.setCellValueFactory(new PropertyValueFactory<Enrollment, String>("roomCourse"));

            TableColumn gradeCol = new TableColumn("Grade");
            gradeCol.setMinWidth(200);
            gradeCol.setCellValueFactory(new PropertyValueFactory<Enrollment, Integer>("grade"));

            table.setItems(data);
            table.getColumns().addAll(nameCol, creditCol, examCol, roomCol, gradeCol);

            HBox hbox = new HBox();
            hbox.setSpacing(5);
            hbox.setPadding(new Insets(10, 0, 0, 10));
            hbox.getChildren().addAll(table);

            back = new Button("Back");
            back.setOnAction(e -> handleBackButton());
            VBox vBox = new VBox();
            vBox.setSpacing(5);
            vBox.setPadding(new Insets(10, 0, 0, 10));
            vBox.getChildren().addAll(hbox, back);

            Scene scene = new Scene(vBox, 1200, 800);

            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(NO_COURSES_TITLE);
            alert.setHeaderText(NO_COURSES_MESSAGE);
            alert.showAndWait();
        }

    }

    private void handleBackButton() {
        window.setScene(sceneMain);
        window.show();
    }

    private void handleUpdateButtonEvent() {

        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String cnp = cnpField.getText();
        int cardNo = Integer.parseInt(cardNoField.getText());
        String group = groupField.getText();

        if (password.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(PASSWORD_TITLE);
            alert.setHeaderText(PASWORD_MESSAGE);
            alert.showAndWait();
        }
        student = (Student) new StudentBuilder()
                .setGroup(group)
                .setCardNo(cardNo)
                .setName(name)
                .setUsername(username)
                .setPassword(password)
                .setEmail(email)
                .setCNP(cnp)
                .build();
        studentService.update(student);

    }

    private void handleDeleteButtonEvent() {
        studentService.removeById(Utility.getLoggedUser());
        window.close();
        try {
            new LoginController(authenticationService, courseService, studentService, userService);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void handleEnrollButtonEnvent(ActionEvent actionEvent) {
    }
}
