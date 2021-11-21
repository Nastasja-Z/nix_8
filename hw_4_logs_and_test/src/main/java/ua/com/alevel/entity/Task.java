package ua.com.alevel.entity;

import java.util.Date;

public class Task {

    private Integer id;
    private Integer workerId;
    private String name;
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", workerId=" + workerId +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
