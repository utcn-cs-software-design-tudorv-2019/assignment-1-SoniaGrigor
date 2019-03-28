package dal.model;

public class Student extends User{

    private int cardNo;
    private String group;

    public Student() {
    }

    public Student(int cardNo, String group ) {
        this.cardNo = cardNo;
        this.group = group;
    }

    public int getCardNo() {
        return cardNo;
    }

    public void setCardNo(int cardNo) {
        this.cardNo = cardNo;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {

        return super.toString()+
                "Student{" +
                "cardNo=" + cardNo +
                ", group='" + group + '\'' +
                '}';
    }
}
