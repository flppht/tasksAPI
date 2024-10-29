package com.filip.tasks.controller;

import com.filip.tasks.model.Task;
import com.filip.tasks.properties.Status;
import com.filip.tasks.service.TaskService;
import com.filip.tasks.service.impl.TasksServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService tasksService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(
            @RequestParam(required=false) Status status,
            @RequestParam(required = false, defaultValue="id") String sortBy) {
        return tasksService.getAllTasks(status, sortBy);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return tasksService.createTask(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        return tasksService.deleteTask(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Task task) {
        return tasksService.updateTask(id, task);
    }
    @PatchMapping("/{id}/complete")
    public ResponseEntity<String> patchTask(@PathVariable int id) {
        return tasksService.completeTask(id);
    }
}
