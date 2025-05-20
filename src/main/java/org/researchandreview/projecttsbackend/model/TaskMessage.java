package org.researchandreview.projecttsbackend.model;


import lombok.Data;

@Data
public class TaskMessage {
    private Integer taskType;
    private Integer taskId;
    private Integer ocrTaskId;
    private Integer transTaskId;
    private Integer ocrResultId;
    private String imageData;
    private String originalText;
    private String translateFrom;
    private String translateTo;
}
