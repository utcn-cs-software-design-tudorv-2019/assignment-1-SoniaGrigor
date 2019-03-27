package bll.course;

import dal.model.Course;
import dal.model.Enrollment;

import java.util.ArrayList;
import java.util.List;

public interface CourseService {
    List<Course> findAll();
    boolean save(Course course);
    void removeAll();
    List<Enrollment> getMyCourses(int id);
    ArrayList<Integer> getIdByName(ArrayList<String> selectedCourses);
}
