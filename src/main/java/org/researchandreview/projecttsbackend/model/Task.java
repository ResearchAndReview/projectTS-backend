package org.researchandreview.projecttsbackend.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Task {
    private Integer id;
    private Integer workingNodeId;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String failCause;
    private String translateFrom;
    private String translateTo;
}