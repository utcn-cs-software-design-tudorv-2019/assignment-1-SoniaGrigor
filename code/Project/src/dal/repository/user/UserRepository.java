package dal.repository.user;

import dal.model.User;
import dal.model.validation.Notification;

import java.util.List;

public interface UserRepository {

    List<User> findAll();
    Notification<User> findByUsernameAndPassword(String username, String password) throws AuthenticationException;
    boolean update (User user);
    boolean save(User user);
    boolean removeAll();
    boolean removeById(int id);
    int getLastIndex();
    User findById(int idUser);
}
