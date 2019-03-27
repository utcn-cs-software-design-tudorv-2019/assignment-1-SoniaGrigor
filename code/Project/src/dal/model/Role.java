package dal.model;

import java.util.List;

public class Role {

    private int id;
    private String role;
    private List<Right> rights;

    public Role(int id, String role, List<Right> rights) {
        this.id = id;
        this.role = role;
        this.rights = rights;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Right> getRights() {
        return rights;
    }

    public void setRights(List<Right> rights) {
        this.rights = rights;
    }
}