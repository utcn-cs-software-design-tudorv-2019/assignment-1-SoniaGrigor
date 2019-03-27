package bll.user;
import dal.model.Role;
import dal.model.User;
import dal.model.builder.UserBuilder;
import dal.model.validation.Notification;
import dal.model.validation.UserValidator;
import dal.repository.security.RightsRolesRepository;
import dal.repository.user.AuthenticationException;
import dal.repository.user.UserRepository;

import java.security.MessageDigest;
import java.util.Collections;

import static dal.database.Constants.Roles.BASIC;

public class AuthenticationServiceMySQL implements AuthenticationService {

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public AuthenticationServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<Boolean> register(String name, String username, String password, String email, String cnp  ) {
        Role basicRole = rightsRolesRepository.findRoleByTitle(BASIC);
        User user = new UserBuilder()
                .setName(name)
                .setUsername(username)
                .setPassword(password)
                .setEmail(email)
                .setCNP(cnp)
                .setRoles(Collections.singletonList(basicRole))
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(encodePassword(password));
            userRegisterNotification.setResult(userRepository.save(user));
        }
        return userRegisterNotification;
    }

    @Override
    public Notification<User> login(String username, String password) throws AuthenticationException {
        return userRepository.findByUsernameAndPassword(username, encodePassword(password));
    }

    @Override
    public boolean logout(User user) {
        return false;
    }

    @Override
    public int getLastIndex() {
        return userRepository.getLastIndex();
    }

    public static String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}