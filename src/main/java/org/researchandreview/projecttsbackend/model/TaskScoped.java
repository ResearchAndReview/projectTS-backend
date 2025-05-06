package org.researchandreview.projecttsbackend.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TaskScoped {
    private Integer id;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String failCause;
    private String translateFrom;
    private String translateTo;

    public TaskScoped(Task task) {
        this.id = task.getId();
        this.status = task.getStatus();
        this.createdAt = task.getCreatedAt();
        this.updatedAt = task.getUpdatedAt();
        this.failCause = task.getFailCause();
        this.translateFrom = task.getTranslateFrom();
        this.translateTo = task.getTranslateTo();
    }
}