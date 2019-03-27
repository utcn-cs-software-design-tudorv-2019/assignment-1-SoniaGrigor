package dal.model.builder;

import dal.model.Role;
import dal.model.User;
import dal.model.Course;

import java.util.List;

public class UserBuilder {

    private User user;

    public UserBuilder() {
        user = new User();
    }

    public UserBuilder setId(int id) {
        user.setId(id);
        return this;
    }

    public UserBuilder setName(String name) {
        user.setName(name);
        return this;
    }

    public UserBuilder setUsername(String username) {
        user.setUsername(username);
        return this;
    }

    public UserBuilder setPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder setEmail(String email) {
        user.setEmail(email);
        return this;
    }

    public UserBuilder setCNP(String cnp) {
        user.setCNP(cnp);
        return this;
    }

    public UserBuilder setCourses(List<Course> courses) {
        user.setCourses(courses);
        return this;
    }

    public UserBuilder setRoles(List<Role> roles) {
        user.setRoles(roles);
        return this;
    }

    public User build() {
        return user;
    }

}