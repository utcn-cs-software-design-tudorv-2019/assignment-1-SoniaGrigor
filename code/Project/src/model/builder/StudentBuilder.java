package model.builder;

import model.Group;
import model.Student;

public class StudentBuilder {

    private Student student;

    public StudentBuilder() {
        student = new Student();
    }

    public StudentBuilder setCardNo(int cardNo){
        student.setCardNo(cardNo);
        return this;
    }

    public StudentBuilder setGroup(String group){
        student.setGroup(group);
        return this;
    }
}
