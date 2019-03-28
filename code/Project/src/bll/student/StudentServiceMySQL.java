package bll.student;

import dal.model.Student;
import dal.model.StudentPersonalInfo;
import dal.repository.course.CourseRepository;
import dal.repository.security.RightsRolesRepository;
import dal.repository.student.StudentRepository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class StudentServiceMySQL implements StudentService {
    private StudentRepository studentRepository;
    private RightsRolesRepository rightsRolesRepository;
    private CourseRepository courseRepository;

    public StudentServiceMySQL(Connection connection, RightsRolesRepository rightsRolesService, CourseRepository courseService, StudentRepository studentRepository) {
        this.studentRepository=studentRepository;
        this.courseRepository=courseService;
        this.rightsRolesRepository=rightsRolesService;
    }

    @Override
    public int findIdByUsernameAndPassword(String username, String password) {
        return studentRepository.findIdByUsernameAndPassword(username,password);
    }

    @Override
    public List<StudentPersonalInfo> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public boolean update(Student student) {
        return studentRepository.update(student);
    }

    @Override
    public boolean save(Student user) {
        return studentRepository.save(user);
    }

    @Override
    public boolean removeAll() {
        return false;
    }

    @Override
    public boolean removeById(int id) {
        return studentRepository.removeById(id);
    }

    @Override
    public boolean enrollCourse(int idUser, int idCourse) {
        return studentRepository.enrollCourse(idUser, idCourse);
    }

    @Override
    public void enrollCourses(int idUser, ArrayList<Integer> idCourses) {
       for(int idCourse=0;idCourse<idCourses.size();idCourse++){
           studentRepository.enrollCourse(idUser, idCourse);
       }
    }

    @Override
    public Student findById(int idUser) {
        return studentRepository.findById(idUser);
    }

    @Override
    public void updateGrade(int idStudent, int idCourse,int grade) {
        studentRepository.updateGrade(idStudent,idCourse, grade);
    }

    @Override
    public boolean updateGroup(int idStudent, String group) {
        return studentRepository.updateGroup(idStudent,group);
    }

}
