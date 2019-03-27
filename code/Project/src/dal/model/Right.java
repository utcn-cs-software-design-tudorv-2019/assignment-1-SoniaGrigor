package dal.model;

public class Right {

    private int id;
    private String right;

    public Right(int id, String right) {
        this.id = id;
        this.right = right;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }
}