package bll.course;

import dal.model.Course;
import dal.model.Enrollment;
import dal.repository.course.CourseRepository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CourseServiceMySQL implements CourseService {
    private final Connection connection;
    private CourseRepository courseRepository;

    public CourseServiceMySQL(Connection connection, CourseRepository courseRepository) {
        this.connection=connection;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public boolean save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void removeAll() {
        courseRepository.removeAll();
    }

    @Override
    public List<Enrollment> getMyCourses(int id) {
        return courseRepository.getMyCourses(id);
    }

    @Override
    public ArrayList<Integer> getIdByName(ArrayList<String> selectedCourses) {
        ArrayList<Integer> idCourses = new ArrayList<>();
        for(int id=0;id<selectedCourses.size();id++) {
            int idCourse = courseRepository.getIdByName(selectedCourses.get(id));
            idCourses.add(idCourse);
        }

         return idCourses;
    }

    @Override
    public int getIdByName(String courseName) {
        return courseRepository.getIdByName(courseName);
    }
}
