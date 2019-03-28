package dal.model;

public class StudentPersonalInfo {
    private int id;
    private String name;
    private int cardNo;
    private String group;

    public StudentPersonalInfo(int id, String name, int cardNo, String group) {
        this.id = id;
        this.name = name;
        this.cardNo = cardNo;
        this.group = group;
    }

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
        return "StudentPersonalInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cardNo=" + cardNo +
                ", group='" + group + '\'' +
                '}';
    }
}
