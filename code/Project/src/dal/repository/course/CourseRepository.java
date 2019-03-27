package dal.repository.course;

import dal.model.Course;
import dal.model.Enrollment;

import java.util.List;

public interface CourseRepository {

    List<Course> findAll();
    boolean save(Course course);
    void removeAll();
    List<Enrollment> getMyCourses(int id);
    int getIdByName(int id);
}
