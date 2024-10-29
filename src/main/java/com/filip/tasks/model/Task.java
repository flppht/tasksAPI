package com.filip.tasks.model;

import com.filip.tasks.properties.Category;
import com.filip.tasks.properties.Priority;
import com.filip.tasks.properties.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private LocalDateTime dueDate;
    @Enumerated(EnumType.STRING)
    private Priority priority;  // "LOW", "MEDIUM", "HIGH"
    @Enumerated(EnumType.STRING)
    private Status status; // "PENDING", "IN_PROGRESS", "COMPLETED"
    @Enumerated(EnumType.STRING)
    private Category category; // "WORK", "PERSONAL", "OTHERS"
    private String userEmail;

    public Task(){}
    public Task(String title, String description, LocalDateTime dueDate, Priority priority, Status status, Category category, String userEmail) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.category = category;
        this.userEmail = userEmail;
    }
}
