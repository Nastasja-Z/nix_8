package ua.com.alevel.dao;

import ua.com.alevel.db.TaskDB;
import ua.com.alevel.entity.Task;

import java.util.Date;

public class TaskDao {

    public void create(Task task) {
        TaskDB.getInstance().create(task);
    }

    public void update(Task task) {
        TaskDB.getInstance().update(task);
    }

    public void delete(Integer id) {
        TaskDB.getInstance().delete(id);
    }

    public void deleteByWorkerId(Integer workerId) {
        TaskDB.getInstance().deleteByWorkerId(workerId);
    }

    public Task findById(Integer id) {
        return TaskDB.getInstance().findById(id);
    }

    public void setSize() {
        TaskDB.getInstance().setSize();
    }

    public boolean existByDate(Date date) {
        return TaskDB.getInstance().existByDate(date);
    }

    public Task findByWorkerId(Integer workerId) {
        return TaskDB.getInstance().findByWorkerId(workerId);
    }

    public Task[] findAll() {
        return TaskDB.getInstance().findAll();
    }
}
