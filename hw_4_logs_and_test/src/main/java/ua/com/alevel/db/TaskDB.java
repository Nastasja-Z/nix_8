package ua.com.alevel.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.entity.Task;

import java.util.Date;

public class TaskDB {

    private static TaskDB instance;
    private final int INITIAL_SIZE = 5;
    private int sizeOfArray;
    private int currentLastIndex;
    private Task[] tasks;

    Logger loggerError = LoggerFactory.getLogger("error");
    Logger loggerInfo = LoggerFactory.getLogger("info");

    public TaskDB() {
        tasks = new Task[INITIAL_SIZE];
        currentLastIndex = 0;
        sizeOfArray = INITIAL_SIZE;
    }

    public void setSize() {
        currentLastIndex = 0;
    }


    public static TaskDB getInstance() {
        if (instance == null) {
            instance = new TaskDB();
        }
        return instance;
    }

    public void create(Task task) {
        if (sizeOfArray == currentLastIndex)
            doubleSize();
        task.setId(generateId());
        tasks[currentLastIndex] = task;
        currentLastIndex++;
    }

    public void update(Task task) {
        if (task.getId() != null) {
            if (task.getWorkerId() != null) {
                if (task.getName() != null) {
                    Task current = findById(task.getId());
                    current.setName(task.getName());
                    current.setDate(task.getDate());
                    current.setWorkerId(task.getWorkerId());
                    loggerInfo.error("updated task"+ current.getId());
                } else {
                    loggerInfo.error("false task name");
                    throw new RuntimeException("CAN`T FIND TASK NAME");
                }
            } else {
                loggerInfo.error("false worker id");
                throw new RuntimeException("CAN`T FIND WORKER_ID");
            }
        } else {
            loggerInfo.error("false task id");
            throw new RuntimeException("CAN`T FIND TASK ID");
        }
    }


    public void delete(Integer id) {
        if (id != null) {
            int dropedItem = 0;
            for (int i = 0; i < currentLastIndex; i++) {
                if (tasks[i].getId().equals(id)) {
                    dropedItem = i;
                }
            }
            for (int j = dropedItem; j < currentLastIndex; j++) {
                tasks[j] = tasks[j + 1];
            }
            currentLastIndex -= 1;
            loggerInfo.info("droped task by id: " + id);
        } else {
            loggerError.error("false id: " + id);
            throw new RuntimeException("CAN`T DELETE TASK BY ID NULL");
        }
    }

    public void deleteByWorkerId(Integer id) {
        if (id != null) {
            int dropedItem = 0;
            for (int i = 0; i < currentLastIndex; i++) {
                if (tasks[i].getWorkerId().equals(id)) {
                    dropedItem = i;
                }
            }
            for (int j = dropedItem; j < currentLastIndex; j++) {
                tasks[j] = tasks[j + 1];
            }
            currentLastIndex -= 1;
            for (int i = 0; i < currentLastIndex; i++) {
                if (tasks[i].getWorkerId().equals(id)) {
                    deleteByWorkerId(id);
                }
            }
        } else {
            loggerError.error("false worker_id: " + id);
            throw new RuntimeException("CAN`T DELETE TASK BY WORKER_ID NULL");
        }
    }

    public boolean existByDate(Date date) {
        for (Task task : tasks) {
            if (task.getDate().equals(date)) {
                return true;
            }
        }
        return false;
    }

    public Task findById(Integer id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        loggerError.error("false task id: " + id);
        throw new RuntimeException("CAN`T FIND TASK BY ID");
    }

    public Task findByWorkerId(Integer workerId) {
        for (Task task : tasks) {
            if (task.getWorkerId().equals(workerId)) {
                return task;
            }
        }
        loggerError.error("false worker_id: ");
        throw new RuntimeException("CAN`T FIND WORKER BY THIS ID");
    }

    public Task[] findAll() {
        return tasks;
    }

    private void doubleSize() {
        Task[] newArray = new Task[sizeOfArray * 2];
        System.arraycopy(tasks, 0, newArray, 0, sizeOfArray);
        tasks = newArray;
        sizeOfArray *= 2;
        loggerInfo.info("double size. now "+sizeOfArray);
    }

    private int getRandomNumber() {
        int min = 1;
        int max = 10_000;
        return (int) ((Math.random() * (max - min)) + min);
    }

    private Integer generateId() {
        Integer id = getRandomNumber();
        for (Task task : tasks) {
            if (task == null) {
                continue;
            }
            if (task.getId().equals(id)) {
                return generateId();
            }
        }
        return id;
    }
}
