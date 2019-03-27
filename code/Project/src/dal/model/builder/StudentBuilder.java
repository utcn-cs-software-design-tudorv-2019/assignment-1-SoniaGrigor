package dal.model.builder;

import dal.model.Student;

public class StudentBuilder extends UserBuilder {

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

    public Student build(){
        return student;
    }
}
