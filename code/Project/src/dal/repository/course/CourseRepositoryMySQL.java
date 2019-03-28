package dal.repository.course;

import dal.model.Course;
import dal.model.Enrollment;
import dal.model.builder.CourseBuilder;
import dal.model.validation.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepositoryMySQL implements CourseRepository {

    private final Connection connection;

    public CourseRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Course> findAll() {
        List<Course> courseList = new ArrayList<Course>();
        Notification<Course> findAllNotification = new Notification<>();
        try {
            PreparedStatement fetchUserSql = connection
                    .prepareStatement("Select * from course;");
            fetchUserSql.executeQuery();

            ResultSet userResultSet = fetchUserSql.getResultSet();

            if (true) {
                while(userResultSet.next()) {
                    Course course = new CourseBuilder()
                            .setId(userResultSet.getInt("id"))
                            .setName(userResultSet.getString("name"))
                            .setCredit(userResultSet.getInt("credit"))
                            .setExam(userResultSet.getDate("exam"))
                            .setRoom(userResultSet.getString("room"))
                            .build();
                    courseList.add(course);
                    findAllNotification.setResult(course);
                }
           } else {
                findAllNotification.addError("Invalid course!");
                return courseList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            findAllNotification.addError("Couldn't find all courses!");
        }
        return courseList;
    }

    @Override
    public boolean save(Course course) {
        Notification<Course> saveNotification = new Notification<>();

        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO course values (null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, course.getName());
            insertUserStatement.setInt(2, course.getCredit());
            insertUserStatement.setDate(3, new java.sql.Date(course.getExam().getTime()));
            insertUserStatement.setString(4, course.getRoom());

            insertUserStatement.executeUpdate();
            saveNotification.setResult(course);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            saveNotification.addError("Couldn't save course!");
            return false;
        }
    }

    @Override
    public void removeAll() {
        Notification<Course> removeNotification = new Notification<>();

        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from course where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            removeNotification.addError("Couldn't delete all courses!");

        }
    }

    @Override
    public List<Enrollment> getMyCourses(int id) {
        List<Enrollment> enrollList = new ArrayList<Enrollment>();
        Notification<Course> findAllNotification = new Notification<>();
        Enrollment enrollment;
        try {
            PreparedStatement fetchUserSql = connection
                    .prepareStatement("SELECT Distinct course.id, course.name, course.credit, course.exam, course.room, user_course.grade " +
                            "FROM course " +
                            "INNER JOIN user_course " +
                            "ON course.id=user_course.course_id " +
                            "WHERE user_course.user_id=?;");
            fetchUserSql.setInt(1,id);
            fetchUserSql.executeQuery();

            ResultSet userResultSet = fetchUserSql.getResultSet();

            if (true) {
                while(userResultSet.next()) {
                    Course course = new CourseBuilder()
                            .setId(userResultSet.getInt("id"))
                            .setName(userResultSet.getString("name"))
                            .setCredit(userResultSet.getInt("credit"))
                            .setExam(userResultSet.getDate("exam"))
                            .setRoom(userResultSet.getString("room"))
                            .build();
                    int grade = userResultSet.getInt("grade");
                    enrollment=new Enrollment(course,grade) ;
                    enrollList.add(enrollment);
                    findAllNotification.setResult(course);
                }
            } else {
                findAllNotification.addError("Invalid course!");
                return enrollList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            findAllNotification.addError("Couldn't find all courses!");

        }
        return enrollList;
    }

    @Override
    public int getIdByName(String courseName) {
        try{
            PreparedStatement fetchCourseSql= connection
                    .prepareStatement("select course.id from course where course.name=?;");
            fetchCourseSql.setString(1,courseName);
            fetchCourseSql.executeQuery();

            ResultSet courseSqlResultSet = fetchCourseSql.getResultSet();
            if(courseSqlResultSet.next()){
                return courseSqlResultSet.getInt("id");
            }
        }catch(SQLException e){
            e.printStackTrace();
            return  -1;
        }
        return -1;
    }
}
