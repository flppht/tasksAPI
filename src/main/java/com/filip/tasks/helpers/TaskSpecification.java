package com.filip.tasks.helpers;

import com.filip.tasks.model.Task;
import com.filip.tasks.properties.Priority;
import com.filip.tasks.properties.Status;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {
    public static Specification<Task> hasStatus(Status status) {
        return ((root, query, criteriaBuilder) -> status==null ? criteriaBuilder.conjunction() : criteriaBuilder.like(root.get("status"), "%" + status + "%"));
    }

    public static Specification<Task> hasPriority(Priority priority) {
        return ((root, query, criteriaBuilder) -> priority==null ? criteriaBuilder.conjunction() : criteriaBuilder.like(root.get("status"), "%" + priority + "%"));
    }
}
