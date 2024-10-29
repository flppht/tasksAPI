package com.filip.tasks.repository;

import com.filip.tasks.model.Task;
import com.filip.tasks.properties.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface TasksRepository extends JpaRepository<Task,Integer>, JpaSpecificationExecutor<Task> {

    List<Task> findAllByDueDateBeforeAndStatus(LocalDateTime dueDate, Status status);
}
