package bll.security;

import dal.model.Right;
import dal.model.Role;
import dal.model.User;

import java.sql.Connection;
import java.util.List;

public class RightsRolesServiceMySQL implements RightsRolesService {

    private final Connection connection;

    public RightsRolesServiceMySQL(Connection connection) {
        this.connection=connection;
    }

    @Override
    public void addRole(String role) {

    }

    @Override
    public void addRight(String right) {

    }

    @Override
    public Role findRoleByTitle(String role) {
        return null;
    }

    @Override
    public Role findRoleById(int roleId) {
        return null;
    }

    @Override
    public Right findRightByTitle(String right) {
        return null;
    }

    @Override
    public void addRolesToUser(User user, List<Role> roles) {

    }

    @Override
    public List<Role> findRolesForUser(int userId) {
        return null;
    }

    @Override
    public void addRoleRight(int roleId, int rightId) {

    }
}
