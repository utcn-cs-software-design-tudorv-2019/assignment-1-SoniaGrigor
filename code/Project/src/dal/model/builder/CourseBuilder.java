package dal.model.builder;

import dal.model.Course;

import java.util.Date;

public class CourseBuilder {
    private Course course;

    public CourseBuilder(){
        course=new Course();
    }

    public CourseBuilder setName(String name) {
        course.setName(name);
        return this;
    }

    public CourseBuilder setCredit(int credit) {
        course.setCredit(credit);
        return this;
    }

    public CourseBuilder setExam(Date date){
        course.setExam(date);
        return this;
    }

    public CourseBuilder setRoom(String room){
        course.setRoom(room);
        return this;
    }

    public Course build(){
        return course;
    }
}
