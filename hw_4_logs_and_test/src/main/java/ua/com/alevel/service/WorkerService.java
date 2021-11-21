package ua.com.alevel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.WorkerDao;
import ua.com.alevel.entity.Worker;

public class WorkerService {

    Logger loggerError = LoggerFactory.getLogger("error");
    Logger loggerInfo = LoggerFactory.getLogger("info");

    private final WorkerDao workerDao= new WorkerDao();

    public void  create(Worker temp){
        if(temp.getName() != null){
            Worker worker = new Worker();
            loggerInfo.info("creation of worker: "+temp.getName());
            worker.setName(temp.getName());
            worker.setPosition(temp.getPosition());
            worker.setStatus(false);
            workerDao.create(worker);
        } else {
            loggerError.error("runtime exception by creation of worker");
            throw new RuntimeException("NAME CAN`T BE NULL");
        }
    }

    public Worker[] findAll() {
        return workerDao.findAll();
    }

    public void update(Worker worker) {
        loggerInfo.info("update of worker: "+worker.getName());
        workerDao.update(worker);
    }

    public Worker findById(Integer id) {
        return workerDao.findById(id);
    }

    public void delete(Integer id) {
        loggerInfo.info("dropped worker by id: "+id);
        workerDao.delete(id);
    }
}
