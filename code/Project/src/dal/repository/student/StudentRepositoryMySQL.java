package dal.repository.student;

import bll.user.AuthenticationServiceMySQL;
import dal.model.Student;
import dal.model.StudentPersonalInfo;
import dal.model.builder.StudentBuilder;
import dal.repository.course.CourseRepository;
import dal.repository.security.RightsRolesRepository;
import utility.Utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryMySQL implements StudentRepository {
    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;
    private final CourseRepository courseRepository;

    public StudentRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository, CourseRepository courseRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
        this.courseRepository = courseRepository;
    }


    @Override
    public int findIdByUsernameAndPassword(String username, String password) {
        try {
            PreparedStatement fetchUserSql = connection
                    .prepareStatement("Select * from USER where `username`= ?  and `password`= ?;");
            fetchUserSql.setString(1, username);
            fetchUserSql.setString(2, password);
            fetchUserSql.executeQuery();

            ResultSet userResultSet = fetchUserSql.getResultSet();
            return userResultSet.getInt("id");

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<StudentPersonalInfo> findAll() {
        List<StudentPersonalInfo> studentList = new ArrayList<StudentPersonalInfo>();
        try {
            PreparedStatement fetchUserSql = connection
                    .prepareStatement("SELECT  user.id, user.name,student.cardNo, student.group_id from student join user on user.id= student.id;");
            fetchUserSql.executeQuery();

            ResultSet studentResultSet = fetchUserSql.getResultSet();

            if (studentResultSet.next()) {
                while (studentResultSet.next()) {
                    int id = Integer.parseInt(studentResultSet.getString("id"));
                    int cardNo = Integer.parseInt(studentResultSet.getString("cardNo"));
                    String group = studentResultSet.getString("group_id");
                    String name = studentResultSet.getString("name");
                    StudentPersonalInfo student = new StudentPersonalInfo(id, name, cardNo, group);
                    studentList.add(student);
                }
                return studentList;
            } else {
                return studentList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Student student) {
        try {
            PreparedStatement updateStudentStatement = connection
                    .prepareStatement("UPDATE user set name=?, username=?, password=?, email=?, cnp=? where user.id =?; ", Statement.RETURN_GENERATED_KEYS);

            updateStudentStatement.setString(1, student.getName());
            updateStudentStatement.setString(2, student.getUsername());
            updateStudentStatement.setString(3, AuthenticationServiceMySQL.encodePassword(student.getPassword()));
            updateStudentStatement.setString(4, student.getEmail());
            updateStudentStatement.setString(5, student.getCNP());
            updateStudentStatement.setInt(6, Utility.getLoggedUser());

            updateStudentStatement.executeUpdate();

            updateStudentStatement = connection
                    .prepareStatement(" UPDATE student set cardNo = ?, group_id=? WHERE student.id=?;", Statement.RETURN_GENERATED_KEYS);

            updateStudentStatement.setInt(1, student.getCardNo());
            updateStudentStatement.setInt(2, Integer.parseInt(student.getGroup()));
            updateStudentStatement.setInt(3, Utility.getLoggedUser());

            updateStudentStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
            return false;
        }
    }

    @Override
    public boolean save(Student student) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?, ?, ?, ?); INSERT INTO student values(null,?,?);", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, student.getName());
            insertUserStatement.setString(2, student.getUsername());
            insertUserStatement.setString(3, student.getPassword());
            insertUserStatement.setString(4, student.getEmail());
            insertUserStatement.setString(5, student.getCNP());
            insertUserStatement.setInt(6, student.getCardNo());
            insertUserStatement.setString(7, student.getGroup());


            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            int studentId = rs.getInt(1);
            student.setId(studentId);

            rightsRolesRepository.addRolesToUser(student, student.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
            return false;
        }

    }

    @Override
    public boolean removeAll() {
        try {
            PreparedStatement deleteUserStatement = connection
                    .prepareStatement("DELETE from student where id>0 ; ", Statement.RETURN_GENERATED_KEYS);
            deleteUserStatement.execute();
            deleteUserStatement = connection
                    .prepareStatement(" DELETE from user where id>0;", Statement.RETURN_GENERATED_KEYS);
            deleteUserStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeById(int id) {
        try {
            PreparedStatement deleteUserStatement = connection
                    .prepareStatement("DELETE from student where id= ? ; ", Statement.RETURN_GENERATED_KEYS);
            deleteUserStatement.setInt(1, id);
            deleteUserStatement.execute();

            deleteUserStatement = connection
                    .prepareStatement(" DELETE from user where id= ?;", Statement.RETURN_GENERATED_KEYS);
            deleteUserStatement.setInt(1, id);
            deleteUserStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean enrollCourse(int idUser, int idCourse) {
        try {
            PreparedStatement enrollUserStatement = connection
                    .prepareStatement("INSERT INTO user_course values (null,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            enrollUserStatement.setInt(1, idUser);
            enrollUserStatement.setInt(2, idCourse);
            enrollUserStatement.setInt(3, 0);
            System.out.println("StudentRepositoryMySQL.enrollCourse" + " " + enrollUserStatement);
            enrollUserStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Student findById(int idUser) {
        try {
            PreparedStatement fetchUserSql = connection
                    .prepareStatement("Select user.id, user.name, user.username, user.password, user.email, user.cnp, student.cardNo, student.group_id " +
                            "from student, user " +
                            "where user.id =?;");
            fetchUserSql.setInt(1, idUser);
            fetchUserSql.executeQuery();

            ResultSet studentResultSet = fetchUserSql.getResultSet();

            if (studentResultSet.next()) {
                Student student = (Student) new StudentBuilder()
                        .setGroup(studentResultSet.getString("group_id"))
                        .setCardNo(Integer.parseInt(studentResultSet.getString("cardNo")))
                        .setId(studentResultSet.getInt("id"))
                        .setName(studentResultSet.getString("name"))
                        .setUsername(studentResultSet.getString("username"))
                        .setPassword(studentResultSet.getString("password"))
                        .setEmail(studentResultSet.getString("email"))
                        .setCNP(studentResultSet.getString("cnp"))
                        .setRoles(rightsRolesRepository.findRolesForUser(studentResultSet.getInt("id")))
                        .build();

                return student;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateGrade(int idStudent, int idCourse, int grade) {
        try {
            PreparedStatement fetchUserSql = connection
                    .prepareStatement("update user_course set grade= ? where user_id=? and course_id=?;");
            fetchUserSql.setInt(1, grade);
            fetchUserSql.setInt(2, idStudent);
            fetchUserSql.setInt(3, idCourse);
            fetchUserSql.executeUpdate();

            ResultSet studentResultSet = fetchUserSql.getResultSet();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateGroup(int idStudent, String group) {
        try {
            PreparedStatement fetchUserSql = connection
                    .prepareStatement("update student set group_id =? where id=?;");
            fetchUserSql.setString(1, group);
            fetchUserSql.setInt(2, idStudent);
            fetchUserSql.executeUpdate();

            ResultSet studentResultSet = fetchUserSql.getResultSet();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
