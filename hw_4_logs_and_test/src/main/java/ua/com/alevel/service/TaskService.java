package ua.com.alevel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.TaskDao;
import ua.com.alevel.entity.Task;

import java.util.Calendar;
import java.util.Date;

public class TaskService {

    Logger loggerError = LoggerFactory.getLogger("error");
    Logger loggerInfo = LoggerFactory.getLogger("info");

    private final TaskDao taskDao = new TaskDao();

    public void create(Task task) {
        Date today = Calendar.getInstance().getTime();
        if (task.getWorkerId() != null) {

            if (task.getName() != null) {
                if (task.getDate().after(today)) {
                    loggerInfo.info("creation of task: " + task.getName());
                    Task current = new Task();
                    current.setName(task.getName());
                    current.setDate(task.getDate());
                    current.setWorkerId(task.getWorkerId());
                    taskDao.create(current);
                }else  {
                    loggerError.error("false date");
                    throw new RuntimeException("FALSE DATE (after today)");
                }
            } else {
                loggerError.error("false name");
                throw new RuntimeException("CAN`T CREATE, BECAUSE OF NAME NULL");
            }
        } else {
            loggerError.error("false worker_id");
            throw new RuntimeException("CAN`T CREATE, BECAUSE OF WORKER_ID NULL");
        }
    }
    public void setSize() {
        taskDao.setSize();
    }

    public Task[] findAll() {
        return taskDao.findAll();
    }

    public Task findById(Integer id) {
        return taskDao.findById(id);
    }

    public void update(Task task) {
        taskDao.update(task);
    }

    public void delete(Integer id) {
        taskDao.delete(id);
    }

    public void deleteByWorkerId(Integer id) {
        taskDao.deleteByWorkerId(id);
    }
}
