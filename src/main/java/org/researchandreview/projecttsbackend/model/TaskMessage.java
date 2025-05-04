package org.researchandreview.projecttsbackend.model;


import lombok.Data;

@Data
public class TaskMessage {
    private Integer taskId;
    private Integer ocrTaskId;
    private String imageData;

    public TaskMessage(int taskId, int ocrTaskId, String base64Encoded) {
        this.taskId = taskId;
        this.ocrTaskId = ocrTaskId;
        this.imageData = base64Encoded;
    }
}
