package dal.model;

import java.util.Date;

public class Course {

    private int id;
    private String name;
    private int credit;
    private Date exam;
    private String room;

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

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", credit=" + credit +
                ", exam=" + exam +
                ", room='" + room + '\'' +
                '}';
    }
}
