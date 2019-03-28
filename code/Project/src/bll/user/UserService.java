package bll.user;

import dal.model.User;
import dal.model.validation.Notification;
import dal.repository.user.AuthenticationException;

import java.util.List;

public interface UserService {
    List<User> findAll();
    Notification<User> findByUsernameAndPassword(String username, String password) throws AuthenticationException;
    boolean update (User user);
    boolean save(User user);
    boolean removeAll();
    boolean removeById(int id);
    User findById(int idUser);

    void generateRaport();
}
