import bll.course.CourseService;
import bll.course.CourseServiceMySQL;
import bll.security.RightsRolesService;
import bll.security.RightsRolesServiceMySQL;
import bll.student.StudentService;
import bll.student.StudentServiceMySQL;
import bll.user.AuthenticationService;
import bll.user.AuthenticationServiceMySQL;
import bll.user.UserService;
import bll.user.UserServiceMySQL;
import dal.database.DBConnectionFactory;
import dal.repository.course.CourseRepository;
import dal.repository.course.CourseRepositoryMySQL;
import dal.repository.security.RightsRolesRepository;
import dal.repository.security.RightsRolesRepositoryMySQL;
import dal.repository.student.StudentRepository;
import dal.repository.student.StudentRepositoryMySQL;
import dal.repository.user.UserRepository;
import dal.repository.user.UserRepositoryMySQL;

import java.sql.Connection;

public class ComponentFactory {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final RightsRolesService rightsRolesService;
    private final StudentService studentService;
    private final CourseService courseService;

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    private static ComponentFactory instance;


    public static synchronized ComponentFactory getInstance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTests) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();

        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        courseRepository=new CourseRepositoryMySQL(connection);
        studentRepository = new StudentRepositoryMySQL(connection,rightsRolesRepository,courseRepository);
        userRepository= new UserRepositoryMySQL(connection,rightsRolesRepository);

        rightsRolesService = new RightsRolesServiceMySQL(connection);
        userService = new UserServiceMySQL(connection, rightsRolesService,userRepository);
        courseService= new CourseServiceMySQL(connection,courseRepository);
        studentService= new StudentServiceMySQL(connection,rightsRolesRepository, courseRepository, studentRepository);
        authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserService getUserService() {
        return userService;
    }

    public RightsRolesService getRightsRolesService() {
        return rightsRolesService;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public CourseRepository getCourseRepository() {
        return courseRepository;
    }

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }
}
