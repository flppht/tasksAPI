package com.filip.tasks;

import com.filip.tasks.model.Task;
import com.filip.tasks.properties.Category;
import com.filip.tasks.properties.Priority;
import com.filip.tasks.properties.Status;
import com.filip.tasks.repository.TasksRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;

@SpringBootApplication
@EnableScheduling
public class TasksApplication {

	public static void main(String[] args) {
		SpringApplication.run(TasksApplication.class, args);
	}


	//initial data in db
	@Bean
	public ApplicationRunner initializer(TasksRepository tasksRepository) {
		return args -> {
			// add initial tasks to db
			tasksRepository.save(
					new Task("Go to store", "Test Buy food and other supplies for house", LocalDateTime.of(LocalDate.parse("2024-11-20"), LocalTime.parse("10:00")), Priority.HIGH, Status.PENDING, Category.PERSONAL, "kulasinacfilip@gmail.com")
			);
			tasksRepository.save(
					new Task("Reply to emails", "Test Reply to emails from boss and clients", LocalDateTime.of(LocalDate.parse("2024-10-29"), LocalTime.parse("10:00")), Priority.MEDIUM, Status.IN_PROGRESS, Category.OTHERS, "kulasinacfilip@gmail.com")
			);


		};
	}
}
