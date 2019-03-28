package presentation.controller;

import bll.course.CourseService;
import bll.student.StudentService;
import bll.user.AuthenticationService;
import bll.user.UserService;
import dal.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.sql.Date;
import java.util.List;

import static utility.ProjectConstants.*;

public class SpecificOperationView {

    Stage window;
    Scene sceneMain;

    int idUser = Utility.getLoggedUser();
    private User user;

    private final AuthenticationService authenticationService;
    private final CourseService courseService;
    private final StudentService studentService;
    private final UserService userService;

    private Button enrollStudentButton;
    private Button viewStudentsButton;
    private Button gradeStudentButton;
    private Button viewEnrollCoursesButton;
    private Button viewAllCoursesButton;
    private Button groupStudentButton;
    private Button back;

    private TextField gradeField;
    private Label gradeLabel;
    private TextField groupField;
    private Label groupLabel;
    private Label instructions;


    private TableView<StudentPersonalInfo> studentTable= new TableView<StudentPersonalInfo>();
    private TableView<Enrollment> courseTable=new TableView<Enrollment>();
    private TableView<Course> allCourseTable = new TableView<Course>();


    public SpecificOperationView(AuthenticationService authenticationService, CourseService courseService, StudentService studentService, UserService userService) {
        window = new Stage();
        window.setTitle(TEACHER_TITLE);

        this.authenticationService = authenticationService;
        this.courseService = courseService;
        this.studentService = studentService;
        this.userService = userService;

        BorderPane layout = new BorderPane();
        layout.setId("root");
        HBox topPane = new HBox(10);
        topPane.setAlignment(Pos.CENTER);
        topPane.setPadding(new Insets(10, 10, 0, 10));
        topPane.getChildren().addAll();


        VBox leftPane = new VBox(10);
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setPadding(new Insets(0, 0, 10, 20));
        instructions=new Label("\nPlease press buttons in order to see data.\nPlease select data from table.");
        viewStudentsButton = new Button("View students");
        viewStudentsButton.setOnAction(e -> handleViewStudentsButtonEvent());
        groupLabel=new Label("Enter a new group ");
        groupField=new TextField();
        groupStudentButton=new Button("Set new group");
        groupStudentButton.setOnAction(e->handleGroupStudentButtonEvent());
        viewAllCoursesButton=new Button("View All Courses");
        viewAllCoursesButton.setOnAction(e->handleViewAllCoursesButtonEvent());
        enrollStudentButton = new Button("Enroll student");
        enrollStudentButton.setOnAction(e -> handleEnrollStudentButtonEvent());
        leftPane.getChildren().addAll(instructions,viewStudentsButton, studentTable,groupLabel,groupField, groupStudentButton, viewAllCoursesButton, allCourseTable,enrollStudentButton);

        VBox rightPane = new VBox(10);
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPadding(new Insets(0, 400, 0, 10));
        viewEnrollCoursesButton = new Button("View Enroll Courses");
        viewEnrollCoursesButton.setOnAction(e -> handleViewEnrollCoursesButtonEvent());
        gradeLabel=new Label("Enter a new grade ");
        gradeField=new TextField();
        gradeStudentButton = new Button("Set new grade");
        gradeStudentButton.setOnAction(e -> handleGradeStudentButtonEvent());
        rightPane.getChildren().addAll(viewEnrollCoursesButton,courseTable,gradeLabel,gradeField, gradeStudentButton);

        VBox bottomPane = new VBox(10);
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setPadding(new Insets(0, 10, 10, 10));
        back= new Button("Back");
        back.setOnAction(e->handleBackButton());
        bottomPane.getChildren().addAll(back);

        layout.setTop(topPane);
        layout.setLeft(leftPane);
        layout.setRight(rightPane);
        layout.setBottom(bottomPane);

        sceneMain = new Scene(layout, 1200, 800);
        window.setScene(sceneMain);
        window.show();
    }

    private void handleGroupStudentButtonEvent() {
        String group=groupField.getText();
        int idStudent =studentTable.getSelectionModel().getSelectedItem().getId();
        if(!studentService.updateGroup(idStudent, group)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(OPERATION_FAILED);
            alert.setHeaderText(OPERATION_FAILED_MESSAGE);
            alert.showAndWait();
        }
    }

    private void handleViewAllCoursesButtonEvent() {
        allCourseTable.getColumns().clear();

        List<Course> myCourseList = courseService.findAll();
        ObservableList<Course> data = FXCollections.observableList(myCourseList);

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));

        TableColumn creditCol = new TableColumn("Credit");
        creditCol.setMinWidth(100);
        creditCol.setCellValueFactory(new PropertyValueFactory<Course, Integer>("credit"));

        TableColumn examCol = new TableColumn("Exam Date");
        examCol.setMinWidth(200);
        examCol.setCellValueFactory(new PropertyValueFactory<Course, Date>("exam"));

        TableColumn roomCol = new TableColumn("Room");
        roomCol.setMinWidth(200);
        roomCol.setCellValueFactory(new PropertyValueFactory<Course, String>("room"));

        allCourseTable.setItems(data);
        allCourseTable.getColumns().addAll(nameCol, creditCol, examCol, roomCol);
    }

    private void handleViewEnrollCoursesButtonEvent() {
        courseTable.getColumns().clear();
        List<Enrollment> myCourseList = courseService.getMyCourses(studentTable.getSelectionModel().getSelectedItem().getId());
        ObservableList<Enrollment> data = FXCollections.observableList(myCourseList);

        TableColumn idCol = new TableColumn("ID");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(new PropertyValueFactory<Enrollment, String>("courseId"));

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

        courseTable.setItems(data);
        courseTable.getColumns().addAll(idCol, nameCol, creditCol, examCol, roomCol, gradeCol);

    }

    private void handleGradeStudentButtonEvent() {
        int idStudent =studentTable.getSelectionModel().getSelectedItem().getId();
        int idCourse = courseTable.getSelectionModel().getSelectedItem().getCourseId();
        int grade = Integer.parseInt(gradeField.getText());
        studentService.updateGrade(idStudent, idCourse, grade);
    }

    private void handleViewStudentsButtonEvent() {
        studentTable.getColumns().clear();

        List<StudentPersonalInfo> studentList=studentService.findAll();
        ObservableList<StudentPersonalInfo> data = FXCollections.observableList(studentList);

        TableColumn idCol = new TableColumn("ID");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(new PropertyValueFactory<User, String>("id"));

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(new PropertyValueFactory<User, String>("name"));

        TableColumn cardCol = new TableColumn("Card No.");
        cardCol.setMinWidth(100);
        cardCol.setCellValueFactory(new PropertyValueFactory<Student, Integer>("cardNo"));

        TableColumn groupCol = new TableColumn("Group");
        groupCol.setMinWidth(200);
        groupCol.setCellValueFactory(new PropertyValueFactory<Student, String>("group"));

        studentTable.setItems(data);
        studentTable.getColumns().addAll(idCol, nameCol, cardCol, groupCol);

    }

    private void handleEnrollStudentButtonEvent() {
        int idStudent =studentTable.getSelectionModel().getSelectedItem().getId();
        int idCourse = allCourseTable.getSelectionModel().getSelectedItem().getId();
        studentService.enrollCourse(idStudent, idCourse);
    }

    private void handleBackButton() {
        window.close();
    }
}
