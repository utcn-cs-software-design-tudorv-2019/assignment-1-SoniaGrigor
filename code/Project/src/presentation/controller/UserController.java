package presentation.controller;

import bll.course.CourseService;
import bll.student.StudentService;
import bll.user.AuthenticationService;
import javafx.stage.Stage;

import static utility.ProjectConstants.TEACHER_TITLE;

public class UserController {

    Stage window;
    private  AuthenticationService authenticationService;
    private StudentService studentService;
    private  CourseService courseService;

    public UserController(AuthenticationService authenticationService, CourseService courseService, StudentService studentService) {
        window = new Stage();
        window.setTitle(TEACHER_TITLE);

        this.authenticationService= authenticationService;
        this.courseService=courseService;
        this.studentService= studentService;

    }
}
