package ua.com.alevel.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.entity.Worker;

public class WorkerDB {

    private static WorkerDB instance;
    private final int INITIAL_SIZE = 10;
    private int sizeOfArray;
    private int currentLastIndex;
    private Worker[] workers;

    Logger loggerError = LoggerFactory.getLogger("error");

    public WorkerDB() {
        workers = new Worker[INITIAL_SIZE];
        currentLastIndex = 0;
        sizeOfArray = INITIAL_SIZE;
    }

    public static WorkerDB getInstance() {
        if (instance == null) {
            instance = new WorkerDB();
        }
        return instance;
    }

    public void create(Worker worker) {
        if (sizeOfArray == currentLastIndex)
            doubleSize();
        worker.setId(generateId());
        workers[currentLastIndex] = worker;
        currentLastIndex++;
    }

    public void update(Worker worker) {
        Worker current = findById(worker.getId());
        current.setName(worker.getName());
        current.setPosition(worker.getPosition());
        current.setStatus(worker.isStatus());
    }

    public void delete(Integer id) {
        if (id != null) {
            int dropedItem = 0;
            for (int i = 0; i < currentLastIndex; i++) {
                if (workers[i].getId().equals(id)) {
                    dropedItem = i;
                }
            }
            for (int j = dropedItem; j < currentLastIndex; j++) {
                workers[j] = workers[j + 1];
            }
            currentLastIndex -= 1;
            TaskDB.getInstance().deleteByWorkerId(id);
        } else {
            loggerError.error("runtime exception by drop of worker (CAN`T FIND WORKER BY ID)");
            throw new RuntimeException("CAN`T FIND WORKER BY ID");
        }
    }

    private void doubleSize() {
        Worker[] newArray = new Worker[sizeOfArray * 2];
        System.arraycopy(workers, 0, newArray, 0, sizeOfArray);
        workers = newArray;
        sizeOfArray *= 2;
    }

    public Worker findById(Integer id) {
        for (Worker worker : workers) {
            if (worker.getId().equals(id)) {
                return worker;
            }
        }
        loggerError.error("runtime exception by find of worker (CAN`T FIND WORKER BY ID)");
        throw new RuntimeException("CAN`T FIND WORKER BY ID");
    }

    public Worker[] findAll() {
        return workers;
    }

    private int getRandomNumber() {
        int min = 1;
        int max = 10_000;
        return (int) ((Math.random() * (max - min)) + min);
    }

    private Integer generateId() {
        Integer id = getRandomNumber();
        for (Worker worker : workers) {
            if (worker == null) {
                continue;
            }
            if (worker.getId().equals(id)) {
                return generateId();
            }
        }
        return id;
    }

}
