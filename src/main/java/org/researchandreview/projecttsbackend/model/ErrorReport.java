package org.researchandreview.projecttsbackend.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ErrorReport {
    private Integer id;
    private Integer taskId;
    private Integer ocrResultId;
    private Integer prevTransId;
    private String newOriginalText;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}