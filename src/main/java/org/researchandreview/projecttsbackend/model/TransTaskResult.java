package org.researchandreview.projecttsbackend.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TransTaskResult {
    private Integer id;
    private Integer ocrResultId;
    private String status;
    private String originalText;
    private String translatedText;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String failCause;
    private String translateFrom;
    private String translateTo;
    private Integer isRecovery;
    private Integer errorReportId;
    private String workingNodeId;
}