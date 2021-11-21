package ua.com.alevel.entity;

public class Worker {

    private Integer id;
    private String name;
    private String position;
    private boolean status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        if (status) {
            return "Worker{" +
                    "access code =" + id +
                    ", name:'" + name + '\'' +
                    ", position:'" + position + '\'' +
                    ", status: you have some tasks" +
                    '}';

        }
        return "Worker{" +
                "access code =" + id +
                ", name:'" + name + '\'' +
                ", position:'" + position + '\'' +
                ", status: no tasks any more" +
                '}';
    }
}
