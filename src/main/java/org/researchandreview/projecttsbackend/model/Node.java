package org.researchandreview.projecttsbackend.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Node {
    private String id;
    private String ip;
    private String status;
    private Timestamp lastAlive;
    private Timestamp createdAt;
    private Integer ocrCount;
    private Integer transCount;
    private Integer contribution;
    private Double ocr_perf;
    private Double trans_perf;
    private Double ocr_task_size;
    private Double trans_task_size;

}