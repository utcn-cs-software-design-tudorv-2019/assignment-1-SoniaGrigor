package bll.student;

import dal.model.Student;
import dal.model.StudentPersonalInfo;

import java.util.ArrayList;
import java.util.List;

public interface StudentService {
    int findIdByUsernameAndPassword(String username, String password);
    List<StudentPersonalInfo> findAll();
    boolean update (Student student);
    boolean save(Student user);
    boolean removeAll();
    boolean removeById(int id);
    boolean enrollCourse(int idUser, int idCourse);
    void enrollCourses(int idUser, ArrayList<Integer> idCourses);

    Student findById(int idUser);

    void updateGrade(int idStudent, int idCourse, int grade);

    boolean updateGroup(int idStudent, String group);
}
