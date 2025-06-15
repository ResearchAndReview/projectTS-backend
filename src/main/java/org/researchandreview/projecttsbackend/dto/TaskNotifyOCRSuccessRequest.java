package org.researchandreview.projecttsbackend.dto;

import lombok.Data;
import org.researchandreview.projecttsbackend.model.Caption;

import java.util.List;

@Data
public class TaskNotifyOCRSuccessRequest {
    private List<Caption> captions;
    private Double elapsedTime;
    private Double ocrTaskSize;
}
