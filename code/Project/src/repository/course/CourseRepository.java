package repository.course;

import model.Course;

import java.util.List;

public interface CourseRepository {

    List<Course> findAll();
    boolean save(Course course);
    void removeAll();

}
