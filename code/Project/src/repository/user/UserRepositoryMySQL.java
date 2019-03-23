package repository.user;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.BASIC;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;

    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll() {
        List<User> userList=new ArrayList<User>();
        Notification<User> findAllNotification = new Notification<>();
        try {
            PreparedStatement fetchUserSql=connection
                    .prepareStatement("Select * from user;");
            fetchUserSql.executeQuery();

            ResultSet userResultSet = fetchUserSql.getResultSet();

            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setName(userResultSet.getString("name"))
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setEmail(userResultSet.getString("email"))
                        .setCNP(userResultSet.getString("cnp"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getInt("id")))
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
            try{
                throw new AuthenticationException();
            }
            catch (AuthenticationException f){
                findAllNotification.addError("Couldn't find all users!");
            }
        }
        return null;
    }

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) throws AuthenticationException {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            //Statement statement = connection.createStatement();
            //String fetchUserSql = "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
            PreparedStatement fetchUserSql=connection
                    .prepareStatement("Select * from USER where `username`= ?  and `password`= ?;");
            fetchUserSql.setString(1,username);
            fetchUserSql.setString(2, password);
            fetchUserSql.executeQuery();

            ResultSet userResultSet = fetchUserSql.getResultSet();

            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setName(userResultSet.getString("name"))
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setEmail(userResultSet.getString("email"))
                        .setCNP(userResultSet.getString("cnp"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getInt("id")))
                        .build();
                findByUsernameAndPasswordNotification.setResult(user);
                return findByUsernameAndPasswordNotification;
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid email or password!");
                return findByUsernameAndPasswordNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AuthenticationException();
        }
    }

    @Override
    public boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getName());
            insertUserStatement.setString(2, user.getUsername());
            insertUserStatement.setString(3, user.getPassword());
            insertUserStatement.setString(4, user.getEmail());
            insertUserStatement.setString(5, user.getCNP());

            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            int userId = rs.getInt(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

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
            String sql = "DELETE from user where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
