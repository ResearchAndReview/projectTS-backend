package org.researchandreview.projecttsbackend.dto;

import lombok.Getter;
import org.researchandreview.projecttsbackend.model.Task;

import java.sql.Timestamp;
import java.util.Date;

@Getter
public class TaskStatusResponse extends GeneralResponse {
    private final String status;
    private final long elapsedTime; // in seconds

    public TaskStatusResponse(Task task) {
        super("Successfully retrieved task status.");
        this.status = task.getStatus();
        this.elapsedTime = calculateElapsedTime(task.getStatus(), task.getCreatedAt(), task.getUpdatedAt());
    }

    private long calculateElapsedTime(String status, Timestamp createdAt, Timestamp updatedAt) {
        Date now = new Date();
        return (switch (status) {
            case "SUCCESS", "FAILED" -> updatedAt.getTime() - createdAt.getTime();
            default -> now.getTime() - createdAt.getTime();
        }) / 1000;
    }
}
