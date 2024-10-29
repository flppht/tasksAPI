package com.filip.tasks.service;

import com.filip.tasks.model.Task;
import com.filip.tasks.properties.Category;
import com.filip.tasks.properties.Priority;
import com.filip.tasks.properties.Status;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

import java.sql.Statement;
import java.util.List;
import java.util.Map;

public interface TaskService {
    ResponseEntity<List<Task>> getAllTasks(Status status, String sortBy);
    ResponseEntity<Task> createTask(Task task);
    ResponseEntity<Task> updateTask(Integer id, Task updatedTask);
    ResponseEntity<String> deleteTask(Integer id);
    ResponseEntity<Task> updatePartialTask(Integer id, Map<String, Object> props);
    ResponseEntity<String> completeTask(Integer id);

}
