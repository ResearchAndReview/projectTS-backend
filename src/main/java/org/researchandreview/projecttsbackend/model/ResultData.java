package org.researchandreview.projecttsbackend.model;

import lombok.Data;

@Data
public class ResultData {
    private Integer ocrResultId;
    private Integer transResultId;
    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;
    private String originalText;
    private String translatedText;
    private String translateFrom;
    private String translateTo;
}
