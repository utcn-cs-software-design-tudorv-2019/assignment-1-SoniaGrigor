package dal.repository.user;

import bll.user.AuthenticationServiceMySQL;
import dal.model.User;
import dal.model.builder.UserBuilder;
import dal.model.validation.Notification;
import dal.repository.security.RightsRolesRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utility.ProjectConstants.LOGGED_USER_FILE;

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

                BufferedWriter writer = new BufferedWriter(new FileWriter(LOGGED_USER_FILE,false));
                writer.write(userResultSet.getString("id"));
                writer.close();

                return findByUsernameAndPasswordNotification;
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid email or password!");
                return findByUsernameAndPasswordNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AuthenticationException();
        } catch (IOException e){
            e.printStackTrace();
            return findByUsernameAndPasswordNotification;
        }
    }

    @Override
    public boolean update(User user) {
        try {
            PreparedStatement updateUserStatement = connection
                    .prepareStatement("UPDATE user set name=?, username=?, password=?, email=?, cnp=? where id =?;", Statement.RETURN_GENERATED_KEYS);
            updateUserStatement.setString(1, user.getName());
            updateUserStatement.setString(2, user.getUsername());
            updateUserStatement.setString(3, AuthenticationServiceMySQL.encodePassword(user.getPassword()));
            updateUserStatement.setString(4, user.getEmail());
            updateUserStatement.setString(5, user.getCNP());
            try {
                updateUserStatement.setInt(6, findByUsernameAndPassword(user.getUsername(),user.getPassword()).getResult().getId());
            } catch (AuthenticationException e) {
                e.printStackTrace();
            }

            updateUserStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
            return false;
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
    public boolean removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from user where id >= 0";
            statement.executeUpdate(sql);
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
                    .prepareStatement("DELETE from user where id= ? ;", Statement.RETURN_GENERATED_KEYS);
            deleteUserStatement.setInt(1, id);
            deleteUserStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getLastIndex() {
        try {
            PreparedStatement lastIndexStatement= connection.prepareStatement("SELECT MAX(id) FROM user;",Statement.RETURN_GENERATED_KEYS);

            lastIndexStatement.executeUpdate();

            ResultSet rs = lastIndexStatement.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e){
            e.printStackTrace();;
            return -1;
        }
    }

    @Override
    public User findById(int idUser) {
        try {
            PreparedStatement fetchUserSql=connection
                    .prepareStatement("Select * from USER where id= ?;");
            fetchUserSql.setInt(1,idUser);
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
                return user;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
