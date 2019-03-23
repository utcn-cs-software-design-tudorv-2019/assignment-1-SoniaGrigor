package service.user;

import model.User;
import model.validation.Notification;
import repository.user.AuthenticationException;

public interface AuthenticationService {

    Notification<Boolean> register(String name, String username, String password, String email, String cnp );
    Notification<User> login(String username, String password) throws AuthenticationException;
    boolean logout(User user);

}