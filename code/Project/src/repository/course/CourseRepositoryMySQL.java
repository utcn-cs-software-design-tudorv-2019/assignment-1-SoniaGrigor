package repository.course;

import model.Course;
import model.builder.CourseBuilder;
import model.validation.Notification;

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
        List<Course> userList=new ArrayList<Course>();
        Notification<Course> findAllNotification = new Notification<>();
        try {
            PreparedStatement fetchUserSql=connection
                    .prepareStatement("Select * from course;");
            fetchUserSql.executeQuery();

            ResultSet userResultSet = fetchUserSql.getResultSet();

            if (userResultSet.next()) {
                Course user = new CourseBuilder()
                        .setName(userResultSet.getString("name"))
                        .setCredit(userResultSet.getInt("credit"))
                        .setExam(userResultSet.getDate("exam"))
                        .setRoom(userResultSet.getString("room"))
                        .build();
                userList.add(user);
                findAllNotification.setResult(user);
                return userList ;
            } else {
                findAllNotification.addError("Invalid email or password!");
                return userList;
            }
        } catch (SQLException e) {
            e.printStackTrace();

                findAllNotification.addError("Couldn't find all courses!");

        }
        return null;
    }

    @Override
    public boolean save(Course course) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO course values (null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, course.getName());
            insertUserStatement.setInt(2, course.getCredit());
            insertUserStatement.setDate(3, new java.sql.Date(course.getExam().getTime()));
            insertUserStatement.setString(4, course.getRoom());

            insertUserStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from course where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
