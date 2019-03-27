package dal.model;

public class Enrollment {
    private Course course;
    private int grade;

    public Enrollment() {
    }

    public Enrollment(Course course, int grade) {
        this.course = course;
        this.grade = grade;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
