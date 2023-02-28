package grades;

public class Homework {
    private String desc;
    private String id;

    public Homework(String desc, String id) {
        this.desc = desc;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Homework{" +
                "desc='" + desc + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
