package dal.repository.student;

import dal.model.Student;

import java.util.List;

public interface StudentRepository {

    int findIdByUsernameAndPassword(String username, String password);
    List<Student> findAll();
    boolean update (Student student);
    boolean save(Student user);
    boolean removeAll();
    boolean removeById(int id);
    boolean enrollCourse( int idUser, int idCourse);
    Student findById(int idUser);
}
