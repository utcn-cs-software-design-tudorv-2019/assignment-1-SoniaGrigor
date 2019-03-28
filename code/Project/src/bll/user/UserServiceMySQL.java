package bll.user;

import bll.security.RightsRolesService;
import dal.model.User;
import dal.model.validation.Notification;
import dal.repository.user.AuthenticationException;
import dal.repository.user.UserRepository;

import java.sql.Connection;
import java.util.List;

public class UserServiceMySQL implements UserService {
    private final Connection connection;
    private final RightsRolesService rightsRolesService;
    private UserRepository userRepository;

    public UserServiceMySQL(Connection connection, RightsRolesService rightsRolesService, UserRepository userRepository) {
        this.connection=connection;
        this.rightsRolesService = rightsRolesService;
        this.userRepository=userRepository;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean save(User user) {
        return false;
    }

    @Override
    public boolean removeAll() {
        return false;
    }

    @Override
    public boolean removeById(int id) {
        return false;
    }

    @Override
    public User findById(int idUser) {
        return userRepository.findById(idUser);
    }

    @Override
    public void generateRaport() {
        userRepository.generateRaport();
    }
}
