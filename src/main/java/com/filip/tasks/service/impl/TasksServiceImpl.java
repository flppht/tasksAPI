package com.filip.tasks.service.impl;


import com.filip.tasks.helpers.TaskSpecification;
import com.filip.tasks.model.Task;
import com.filip.tasks.properties.Category;
import com.filip.tasks.properties.Priority;
import com.filip.tasks.properties.Status;
import com.filip.tasks.repository.TasksRepository;
import com.filip.tasks.service.EmailService;
import com.filip.tasks.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TasksServiceImpl implements TaskService {

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public ResponseEntity<List<Task>> getAllTasks(Status status, String sortBy) {
        Specification<Task> spec = Specification.where(TaskSpecification.hasStatus(status));

        Sort sort = Sort.by(Sort.Direction.fromString("ASC"), sortBy);

        List<Task> tasks = tasksRepository.findAll(spec, sort);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Task> createTask(Task task) {
        Task newTask = new Task(task.getTitle(), task.getDescription(), task.getDueDate(), task.getPriority(), task.getStatus(), task.getCategory(), task.getUserEmail());
        tasksRepository.save(newTask);

        emailService.sendEmail(task.getUserEmail(), "New task assigned to you!", "Task: '" + task.getTitle() + "' was created on your name. \n\nDue date is: " + task.getDueDate());

        return new ResponseEntity<>(newTask, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteTask(Integer id) {
        try {
            Optional<Task> task = tasksRepository.findById(id);
            if (task.isEmpty()) {
                return new ResponseEntity<>("Task with certain ID does not exist!", HttpStatus.NOT_FOUND);
            }
            tasksRepository.deleteById(id);
            return new ResponseEntity<>("Successfully deleted task with ID: " + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
        public ResponseEntity<Task> updatePartialTask(Integer id, Map<String, Object> props) {
            Task task = tasksRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found!"));

            props.forEach((key, value) -> {
                switch (key) {
                    case "dueDate":
                        task.setDueDate((LocalDateTime) value);
                        break;
                    case "status":
                        task.setStatus((Status) value);
                        break;
                    case "priority":
                        task.setPriority((Priority) value);
                        break;
                    case "title":
                        task.setTitle((String) value);
                        break;
                    case "description":
                        task.setDescription((String) value);
                        break;
                    case "category":
                        task.setCategory((Category) value);
                        break;
                    case "userEmail":
                        task.setUserEmail((String) value);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid property: " +key);
                }
            });

            tasksRepository.save(task);

            return new ResponseEntity<>(task, HttpStatus.OK);
        }

        @Override
        public ResponseEntity<Task> updateTask (Integer id, Task updatedTask) {
            Task task = tasksRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found!"));

            updatedTask.setId(task.getId());

            tasksRepository.save(updatedTask);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        }

        @Override
        public ResponseEntity<String> completeTask(Integer id) {
            Task task = tasksRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found!"));

            task.setStatus(Status.COMPLETED);

            tasksRepository.save(task);
            return new ResponseEntity<>("Task set as 'completed'", HttpStatus.OK);
        }
}
