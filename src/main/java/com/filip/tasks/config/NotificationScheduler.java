package com.filip.tasks.config;
import com.filip.tasks.controller.AdminConfigController;
import com.filip.tasks.model.Task;
import com.filip.tasks.properties.Status;
import com.filip.tasks.repository.TasksRepository;
import com.filip.tasks.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotificationScheduler {


    private final EmailService emailService;
    private final TasksRepository taskRepository;

    private final AdminConfigController adminConfigController;

    @Autowired
    public NotificationScheduler(EmailService emailService, TasksRepository taskRepository, AdminConfigController adminConfigController) {
        this.emailService = emailService;
        this.taskRepository = taskRepository;
        this.adminConfigController = adminConfigController;
    }

    @Scheduled(cron = "0 * * * * ?") // Runs every 1min, adjust as needed
    public void checkOverdueTasks() {
        try {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("admin", null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));


            Status triggerStatus = adminConfigController.getNotificationTriggerStatus();
            if (triggerStatus == null) {
                System.out.println("No trigger status configured by admin.");
                return;
            }

            LocalDateTime now = LocalDateTime.now();
            List<Task> overdueTasks = taskRepository.findAllByDueDateBeforeAndStatus(now, triggerStatus);

            for (Task task : overdueTasks) {
                emailService.sendEmail(task.getUserEmail(), "Reminder for task: " + task.getTitle(), task.getDescription());
            }
        }
        finally {
            SecurityContextHolder.clearContext();
        }
    }

}


