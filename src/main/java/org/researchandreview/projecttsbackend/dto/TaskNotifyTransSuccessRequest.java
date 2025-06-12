package org.researchandreview.projecttsbackend.dto;

import lombok.Data;

@Data
public class TaskNotifyTransSuccessRequest {
    private String translatedText;
    private double elapsedTime;
}
