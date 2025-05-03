package org.researchandreview.projecttsbackend.model;


import lombok.Data;

@Data
public class TaskMessage {
    private Integer taskId;
    private String imageData;

    public TaskMessage(int taskId, String base64Encoded) {
        this.taskId = taskId;
        this.imageData = base64Encoded;
    }
}
