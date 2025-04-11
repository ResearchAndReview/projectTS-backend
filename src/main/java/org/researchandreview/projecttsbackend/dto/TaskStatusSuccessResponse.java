package org.researchandreview.projecttsbackend.dto;

import lombok.Getter;
import org.researchandreview.projecttsbackend.model.OCRResult;
import org.researchandreview.projecttsbackend.model.Task;
import org.researchandreview.projecttsbackend.model.TransTaskResult;

import java.util.List;

@Getter
public class TaskStatusSuccessResponse extends TaskStatusResponse {
    private final List<OCRResult> ocrResults;
    private final List<TransTaskResult> transTaskResults;

    public TaskStatusSuccessResponse(Task task, List<OCRResult> ocrResults, List<TransTaskResult> transResults) {
        super(task);
        this.ocrResults = ocrResults;
        this.transTaskResults = transResults;
    }
}
