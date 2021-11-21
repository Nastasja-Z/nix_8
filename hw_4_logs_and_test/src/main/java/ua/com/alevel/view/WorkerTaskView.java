package ua.com.alevel.view;

import ua.com.alevel.entity.Task;
import ua.com.alevel.entity.Worker;
import ua.com.alevel.service.TaskService;
import ua.com.alevel.service.WorkerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

public class WorkerTaskView {

    private final WorkerService workerService = new WorkerService();
    private final TaskService taskService = new TaskService();

    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nTHIS IS YOUR PERSONAL TASKER \nAt first you need registration: ");
        createProfile(reader);
        String position;
        try {
            runNavigation();
            while ((position = reader.readLine()) != null) {
                crud(position, reader);
                position = reader.readLine();
                crud(position, reader);
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void runNavigation() {
        System.out.println();
        System.out.println("1 - Update your profile (check your id at first)");
        System.out.println("2 - See your profile");
        System.out.println("3 - Delete your profile");
        System.out.println("4 - Create a task");
        System.out.println("5 - Update task");
        System.out.println("6 - Delete task");
        System.out.println("7 - See all your tasks");
        System.out.println("0 - exit");
        System.out.println();
    }

    private void crud(String position, BufferedReader reader) {
        switch (position) {
            case "1":
                updateProfile(reader);
                break;
            case "2":
                findById(reader);
                break;
            case "3":
                deleteProfile(reader);
                break;
            case "4":
                createTask(reader);
                break;
            case "5":
                updateTask(reader);
                break;
            case "6":
                deleteTask(reader);
                break;
            case "7":
                findAllTasks(reader);
                break;
            case "0":
                System.exit(0);
        }
        runNavigation();
    }

    private void deleteTask(BufferedReader reader) {
        try {
            System.out.println("Please, enter access code");
            String id = reader.readLine();
            int idw = Integer.parseInt(id);
            System.out.println("Please, enter id of task, to delete");
            String idTask = reader.readLine();
            int idt = Integer.parseInt(idTask);
            Task[] tasks = taskService.findAll();
            if (taskService.findById(idt).getWorkerId().equals(idw)) {
                taskService.delete(idt);
                int length = 0;
                for (Task task : tasks) {
                    if (task != null) length += 1;
                }
                if (length == 0) workerService.findById(idw).setStatus(false);
            } else {
                System.out.println("It`s not your task");
            }

        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void updateTask(BufferedReader reader) {
        try {
            System.out.println("Please, enter your access code");
            String id = reader.readLine();
            int idw = Integer.parseInt(id);
            System.out.println("Please, enter id of the task");
            String idTask = reader.readLine();
            int idt = Integer.parseInt(idTask);
            System.out.println("Please, enter NAME OF TASK");
            String name = reader.readLine();
            System.out.println("Please, enter DEADLINE -- format (yyyy.MM.dd)");
            String position = reader.readLine();
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy.MM.dd").parse(position);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Task task = new Task();
            task.setId(idt);
            task.setName(name);
            task.setDate(date);
            task.setWorkerId(idw);
            taskService.update(task);
            System.out.println();
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void findAllTasks(BufferedReader reader) {
        System.out.println("Please, enter your access code");
        String ac = null;
        Integer idw = null;
        try {
            ac = reader.readLine();
            idw = Integer.parseInt(ac);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        Task[] tasks = taskService.findAll();
        if (tasks != null && tasks.length != 0) {
            int id = 0;
            for (Task task : tasks) {
                if (task != null && task.getWorkerId().equals(idw)) {
                    id++;
                    System.out.println(task);
                }
            }
        } else {
            System.out.println("you haven`t got any tasks");
        }
    }

    private void createTask(BufferedReader reader) {
        try {
            System.out.println("Please, enter your access code");
            String id = reader.readLine();
            int idw = Integer.parseInt(id);
            System.out.println("Please, enter NAME OF TASK");
            String name = reader.readLine();
            System.out.println("Please, enter DEADLINE -- format (yyyy.MM.dd)");
            String position = reader.readLine();
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy.MM.dd").parse(position);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Task task = new Task();
            task.setWorkerId(idw);
            task.setName(name);
            task.setDate(date);
            taskService.create(task);
            workerService.findById(idw).setStatus(true);
            System.out.println();
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void deleteProfile(BufferedReader reader) {
        try {
            System.out.println("Please, enter access code");
            String id = reader.readLine();
            int idw = Integer.parseInt(id);
            Task[] tasks = taskService.findAll();
            for (Task task : tasks) {
                if (task != null) {
                    taskService.delete(task.getId());
                }
            }
            workerService.delete(idw);
            System.out.println("Dou you want create new account?");
            String yes = reader.readLine();
            if (yes.equals("yes")) {
                createProfile(reader);
                taskService.setSize();
            } else {
                System.exit(0);
            }
        } catch (
                IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }

    }

    private void findById(BufferedReader reader) {
        try {
            System.out.println("Please, enter access code");
            String id = reader.readLine();
            Integer idw = Integer.parseInt(id);
            Worker worker = workerService.findById(idw);
            System.out.println("your information: " + worker);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void updateProfile(BufferedReader reader) {
        try {
            System.out.println("Please, enter access code");
            String id = reader.readLine();
            Integer idw = Integer.parseInt(id);
            System.out.println("Please, enter your new NAME");
            String name = reader.readLine();
            System.out.println("Please, enter new name of your JOB");
            String position = reader.readLine();
            Worker worker = new Worker();
            worker.setId(idw);
            worker.setName(name);
            worker.setPosition(position);
            workerService.update(worker);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void createProfile(BufferedReader reader) {
        try {
            System.out.println("Please, enter your NAME");
            String name = reader.readLine();
            System.out.println("Please, enter name of your JOB");
            String position = reader.readLine();
            Worker worker = new Worker();
            worker.setName(name);
            worker.setPosition(position);
            workerService.create(worker);
            Worker[] workers = workerService.findAll();
            System.out.println("Your access code is: " + workers[0].getId() + "" +
                    "\n you need it for work with your profile");
            System.out.println();
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }
}
