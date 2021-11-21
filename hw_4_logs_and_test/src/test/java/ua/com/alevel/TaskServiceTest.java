package ua.com.alevel;

import org.junit.jupiter.api.*;

import ua.com.alevel.entity.Task;
import ua.com.alevel.entity.Worker;
import ua.com.alevel.service.TaskService;
import ua.com.alevel.service.WorkerService;

import static ua.com.alevel.TestHelper.TEST;
import static ua.com.alevel.TestHelper.TEST_DATE;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskServiceTest {

    private final Task task = new Task();
    private final Worker worker = new Worker();
    private final WorkerService workerService = new WorkerService();
    private final TaskService taskService = new TaskService();

    @Test
    @Order(1)
    public void createTaskIfWorkerIdOrNameExistThrow() {
        worker.setName(TEST);
        worker.setPosition(TEST);
        worker.setId(1);

        workerService.create(worker);
        Worker[] workers = workerService.findAll();

        task.setName(TEST);
        task.setWorkerId(null);
        task.setDate(TEST_DATE);

        Assertions.assertThrows(RuntimeException.class,
                () -> taskService.create(task), "CAN`T CREATE, BECAUSE OF WORKER_ID NULL");

        task.setName(null);
        task.setWorkerId(workers[0].getId());

        Assertions.assertThrows(RuntimeException.class,
                () -> taskService.create(task), "CAN`T CREATE, BECAUSE OF NAME NULL");

    }

    @Test
    @Order(2)
    public void createTaskIfWorkerIdExist() {
        worker.setName(TEST);
        worker.setPosition(TEST);

        workerService.create(worker);
        Worker[] workers = workerService.findAll();

        task.setName(TEST);
        task.setWorkerId(workers[0].getId());
        task.setDate(TEST_DATE);

        taskService.create(task);
        Task[] tasks = taskService.findAll();
        Assertions.assertNotNull(tasks[0]);
        Assertions.assertEquals(tasks[0].getName(), TEST);
        Assertions.assertEquals(tasks[0].getDate(), TEST_DATE);
        Assertions.assertEquals(tasks[0].getWorkerId(), workers[0].getId());
    }

    @Test
    @Order(3)
    public void returnTaskIfIdIsEmpty() {
        Assertions.assertThrows(RuntimeException.class, () -> taskService.findById(null), "CAN`T FIND TASK BY ID");
    }

    @Test
    @Order(4)
    public void returnTaskArrayIfResponseIsNotEmpty() {
        Task[] tasks = taskService.findAll();
        Worker[] workers = workerService.findAll();

        Assertions.assertEquals(tasks[0].getName(), TEST);
        Assertions.assertEquals(workers[0].getId(), tasks[0].getWorkerId());
    }

    @Test
    @Order(5)
    public void updateTaskWithoutThrow() {
        Task[] tasks = taskService.findAll();
        Task currentTask = tasks[0];
        Worker[] workers = workerService.findAll();

        Task t = new Task();
        t.setId(currentTask.getId());
        t.setName(TEST);
        t.setDate(TEST_DATE);
        t.setWorkerId(workers[0].getId());

        Assertions.assertDoesNotThrow(() -> taskService.update(t));
    }

    @Test
    @Order(6)
    public void updateTaskWithThrowOfId() {
        Worker[] workers = workerService.findAll();

        Task current = new Task();
        current.setId(null);
        current.setName(TEST);
        current.setDate(TEST_DATE);
        current.setWorkerId(workers[0].getId());

        Assertions.assertThrows(RuntimeException.class,()->taskService.update(current),"CAN`T FIND TASK BY ID");
    }

    @Test
    @Order(7)
    public void updateTaskWithThrowOfName() {
        Worker[] workers = workerService.findAll();
        Task[] tasks = taskService.findAll();

        Task current = new Task();
        current.setId(tasks[0].getId());
        current.setName(null);
        current.setDate(TEST_DATE);
        current.setWorkerId(workers[0].getId());

        Assertions.assertThrows(RuntimeException.class,()->taskService.update(current),"CAN`T FIND TASK NAME");
    }

    @Test
    @Order(8)
    public void updateTaskWithThrowOfWorkerId() {
        Worker[] workers = workerService.findAll();
        Task[] tasks = taskService.findAll();

        Task current = new Task();
        current.setId(tasks[0].getId());
        current.setName(TEST);
        current.setDate(TEST_DATE);
        current.setWorkerId(null);

        Assertions.assertThrows(RuntimeException.class,()->taskService.update(current),"CAN`T FIND WORKER_ID");
    }

    @Test
    @Order(9)
    public void returnTaskIfIdIsNotEmpty() {
        Task[] tasks= taskService.findAll();
        Task current=tasks[0];

        Assertions.assertNotNull(current);
        Assertions.assertEquals(current.getName(),TEST);
        Assertions.assertEquals(current.getDate(),TEST_DATE);
    }

    @Test
    @Order(10)
    public void deleteTaskIfIdIsEmpty() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> taskService.delete(null),
                "CAN`T FIND TASK BY ID NULL");


        Task[] tasks=taskService.findAll();
        Integer current=tasks[0].getId();

        Assertions.assertNotNull(tasks[0]);
        Assertions.assertDoesNotThrow(() -> taskService.delete(current));
        Assertions.assertEquals(tasks[0], null);
    }

}
