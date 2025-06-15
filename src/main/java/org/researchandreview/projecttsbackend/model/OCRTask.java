package org.researchandreview.projecttsbackend.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class OCRTask {
    private Integer id;
    private Integer taskId;
    private String failCause;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String workingNodeId;
}