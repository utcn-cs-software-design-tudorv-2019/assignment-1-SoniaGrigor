package dal.model;

import java.util.Date;

public class Enrollment {
    private Course course;
    private int courseId;
    private int grade;
    private String courseName;
    private int creditCourse;
    private Date dateExam;
    private String roomCourse;
    public Enrollment() {
    }

    public Enrollment(Course course, int grade) {
        this.course = course;
        this.grade = grade;
        this.courseId=course.getId();
        this.courseName=course.getName();
        this.creditCourse=course.getCredit();
        this.dateExam=course.getExam();
        this.roomCourse=course.getRoom();

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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCreditCourse() {
        return creditCourse;
    }

    public void setCreditCourse(int creditCourse) {
        this.creditCourse = creditCourse;
    }

    public Date getDateExam() {
        return dateExam;
    }

    public void setDateExam(Date dateExam) {
        this.dateExam = dateExam;
    }

    public String getRoomCourse() {
        return roomCourse;
    }

    public void setRoomCourse(String roomCourse) {
        this.roomCourse = roomCourse;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "course=" + course +
                ", courseId=" + courseId +
                ", grade=" + grade +
                ", courseName='" + courseName + '\'' +
                ", creditCourse=" + creditCourse +
                ", dateExam=" + dateExam +
                ", roomCourse='" + roomCourse + '\'' +
                '}';
    }
}
