package dal.model;

import java.util.Date;

public class Course {

    private int id;
    private String name;
    private int credit;
    private Date exam;
    private String room;
    private int grade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public Date getExam() {
        return exam;
    }

    public void setExam(Date exam) {
        this.exam = exam;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
