package ua.com.alevel;

import org.junit.jupiter.api.*;

import ua.com.alevel.entity.Worker;
import ua.com.alevel.service.WorkerService;

import static ua.com.alevel.TestHelper.TEST;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WorkerServiceTest {

    private final Worker worker=new Worker();
    private final WorkerService workerService=new WorkerService();

    @Test
    @Order(1)
    public void workerNameShouldNotBeNull() {
        worker.setName(TEST);
        worker.setPosition(TEST);

        workerService.create(worker);
        Worker[] workers=workerService.findAll();

        Assertions.assertNotNull(workers);
        Assertions.assertEquals(workers[0].getName(),TEST);
        Assertions.assertNotNull(workers[0].getName());
    }

    @Test
    @Order(2)
    public void workerNameShouldNotBeNullThrow(){
        worker.setName(null);
        worker.setPosition(TEST);

        Assertions.assertThrows(
                RuntimeException.class,
                () -> workerService.create(worker),
                "NAME CAN`T BE NULL");

    }

    @Test
    @Order(3)
    public void returnWorkerIfIdIsEmpty() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> workerService.findById(null),
                "CAN`T FIND WORKER BY ID");
    }

    @Test
    @Order(4)
    public void returnWorkerArrayIfResponseIsNotEmpty() {
        Worker[] workers = workerService.findAll();

        Assertions.assertFalse(workers[0].isStatus());
    }

    @Test
    @Order(5)
    public void updateWorkerWithoutThrow() {
        Worker[] workers= workerService.findAll();
        Worker current= workers[0];

        Worker w=new Worker();
        w.setId(current.getId());
        w.setName(TEST);
        w.setPosition(TEST);

        Assertions.assertDoesNotThrow(()->workerService.update(w));
    }

    @Test
    @Order(6)
    public void updateWorkerWithThrow() {
        Worker current=new Worker();
        current.setId(null);
        current.setName(TEST);
        current.setPosition(TEST);

        Assertions.assertThrows(RuntimeException.class,()->workerService.update(current),"CAN`T FIND WORKER BY ID");
    }

    @Test
    @Order(7)
    public void returnWorkerIfIdIsNotEmpty() {
        Worker[] workers= workerService.findAll();
        Worker current=workers[0];

        Assertions.assertNotNull(current);
        Assertions.assertEquals(current.getName(),TEST);
        Assertions.assertEquals(current.isStatus(),false);
    }

    @Test
    @Order(8)
    public void deleteWorkerIfIdIsEmpty() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> workerService.delete(null),
                "CAN`T FIND WORKER BY ID");

        Worker[] workers=workerService.findAll();
        Integer current=workers[0].getId();

        Assertions.assertNotNull(workers[0]);
        Assertions.assertDoesNotThrow(() -> workerService.delete(current));
        Assertions.assertEquals(workers[0], null);
    }
}
