package ua.com.alevel.dao;

import ua.com.alevel.db.WorkerDB;
import ua.com.alevel.entity.Worker;

public class WorkerDao {

    public void create(Worker worker) {
        WorkerDB.getInstance().create(worker);
    }

    public void update(Worker product) {
        WorkerDB.getInstance().update(product);
    }

    public void delete(Integer id) {
        WorkerDB.getInstance().delete(id);
    }

    public Worker findById(Integer id) {
        return WorkerDB.getInstance().findById(id);
    }

    public Worker[] findAll() {
        return WorkerDB.getInstance().findAll();
    }
}
