package org.researchandreview.projecttsbackend.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class TaskNotifyOCRSuccessResponse extends GeneralResponse {
    private final List<Integer> createdTransTaskId;

    public TaskNotifyOCRSuccessResponse(List<Integer> createdTransTaskId) {
        super("OCR Successfully Reported");
        this.createdTransTaskId = createdTransTaskId;
    }
}
