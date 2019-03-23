package model.builder;

import model.Group;

public class GroupBuilder {
    private Group group;

    public GroupBuilder() {
        group = new Group();
    }

    public GroupBuilder setId(int id) {
        group.setId(id);
        return this;
    }

    public GroupBuilder setAuthor(String name) {
        group.setName(name);
        return this;
    }

    public Group build() {
        return group;
    }

}
