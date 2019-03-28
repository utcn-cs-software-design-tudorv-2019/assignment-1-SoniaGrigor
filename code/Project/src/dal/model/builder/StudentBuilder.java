package dal.model.builder;

import dal.model.Course;
import dal.model.Role;
import dal.model.Student;

import java.util.List;

public class StudentBuilder {

    private Student student;

    public StudentBuilder() {
        student = new Student();
    }

    public StudentBuilder setId(int id) {
        student.setId(id);
        return this;
    }

    public StudentBuilder setName(String name) {
        student.setName(name);
        return this;
    }

    public StudentBuilder setUsername(String studentname) {
        student.setUsername(studentname);
        return this;
    }

    public StudentBuilder setPassword(String password) {
        student.setPassword(password);
        return this;
    }

    public StudentBuilder setEmail(String email) {
        student.setEmail(email);
        return this;
    }

    public StudentBuilder setCNP(String cnp) {
        student.setCNP(cnp);
        return this;
    }

    public StudentBuilder setCourses(List<Course> courses) {
        student.setCourses(courses);
        return this;
    }

    public StudentBuilder setRoles(List<Role> roles) {
        student.setRoles(roles);
        return this;
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
