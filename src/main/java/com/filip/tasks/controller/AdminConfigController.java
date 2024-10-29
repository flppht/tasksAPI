package com.filip.tasks.controller;

import com.filip.tasks.properties.Status;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
public class AdminConfigController {

    private Status notificationTriggerStatus;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/statusNotification")
    public String configureNotificationTriggerStatus(@RequestParam Status status) {
        this.notificationTriggerStatus = status;
        return "Notification trigger status set to: " + notificationTriggerStatus;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getNotificationTriggerStatus")
    public Status getNotificationTriggerStatus() {
        return this.notificationTriggerStatus;
    }
}
