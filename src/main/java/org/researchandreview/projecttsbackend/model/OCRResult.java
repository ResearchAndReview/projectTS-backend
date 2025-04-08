package org.researchandreview.projecttsbackend.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class OCRResult {
    private Integer id;
    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;
    private Integer ocrTaskId;
}